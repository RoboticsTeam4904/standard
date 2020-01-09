package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.commands.CustomCommand;

/**
 * Idles the motor (sets speed to 0).
 *
 */
public class MotorIdle extends CustomCommand {
	protected final Motor motor;

	/**
	 * Constructor.
	 *
	 * @param motor
	 */
	public MotorIdle(Motor motor) {
		super("motorIdle", motor);
		this.motor = motor;
		LogKitten.d("MotorIdle created");
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
