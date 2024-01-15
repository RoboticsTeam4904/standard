package org.usfirst.frc4904.standard.commands.motor;

// import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Sets a motor to a speed. The speed can change through use of the set command.
 * This is better than setting the motor because it uses requires to avoid
 * having multiple attempts to set a motor simultaneously.
 *
 */
@Deprecated
public class MotorSet extends Command {
	protected final MotorController motor;
	protected double speed;

	public MotorSet(String name, Motor motor) {
		super();
		setName(name);
		addRequirements(motor);
		this.motor = motor;
		speed = 0;
		// LogKitten.d("MotorSet created for " + motor.getName());
	}

	@Override
	public void initialize() {
		// LogKitten.d("MotorSet initialized");
	}

	/**
	 * Set the speed of the motor
	 */
	public void set(double speed) {
		this.speed = speed;
		// LogKitten.d("MotorSet writePipe set to " + speed);
	}

	@Override
	public void execute() {
		motor.set(speed);
		// LogKitten.d("MotorSet executing with speed " + speed);
	}

	@Override
	public void end(boolean interrupted) {
		if (!interrupted) {
			motor.set(0);
			// LogKitten.d("MotorSet ended (motor speed set to 0)");
		} else {
			// LogKitten.d("MotorSet interrupted (motor speed undefined)");
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
