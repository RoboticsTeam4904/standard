package org.usfirst.frc4904.standard.commands.motor;

import java.util.Set;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.Set;
import java.util.HashSet;

/**
 * Sets a motor to a speed. The speed can change through use of the set command.
 * This is better than setting the motor because it uses requires to avoid
 * having multiple attempts to set a motor simultaneously.
 *
 */
public class MotorSet implements Command {
	protected final SpeedController motor;
	protected double speed;

	public MotorSet(Motor motor) {
		this.motor = motor;
		speed = 0;
		LogKitten.d("MotorSet created for " + motor.getName());
		//requires(motor);
	}

	@Override
	public Set<SpeedController> getRequirements() {
		HashSet<SpeedController> set = new HashSet<SpeedController>();
		set.add(this.motor);
		return set;
	}
	
	@Override
	public void initialize() {
		LogKitten.d("MotorSet initialized");
	}

	/**
	 * Set the speed of the motor
	 */
	public void set(double speed) {
		this.speed = speed;
		LogKitten.d("MotorSet writePipe set to " + speed);
	}

	@Override
	protected void execute() {
		motor.set(speed);
		LogKitten.d("MotorSet executing with speed " + speed);
	}

	@Override
	public void end(boolean interrupted) {
		motor.set(0);
		if (interrupted) {
			LogKitten.d("MotorSet interrupted?!");
		} else {
			LogKitten.d("MotorSet ended (motor speed set to 0)");
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public Set<Subsystem> getRequirements() {
		return null;
	}
}
