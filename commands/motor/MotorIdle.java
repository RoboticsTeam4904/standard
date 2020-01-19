package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Idles the motor (sets speed to 0).
 *
 */
public class MotorIdle extends CommandBase {
	protected final Motor motor;

	/**
	 * Constructor.
	 *
	 * @param name
	 * @param motor
	 */
	public MotorIdle(String name, Motor motor) {
		super();
		setName(name);
		addRequirements(motor);
		this.motor = motor;
		LogKitten.d("MotorIdle created");
	}

	/**
	 * Constructor.
	 * 
	 * @param motor
	 */
	public MotorIdle(Motor motor) {
		this("MotorIdle", motor);
	}

	@Override
	public void initialize() {
		motor.set(0);
		LogKitten.d("MotorIdle initialized");
	}

	@Override
	public void execute() {
		motor.set(0);
		LogKitten.d("MotorIdle executing");
	}

	@Override
	public boolean isFinished() {
		return false; // default command
	}

	@Override
	public void end(boolean interrupted) {
		if (interrupted) {
			LogKitten.d("MotorIdle interrupted");
		}
	}
}
