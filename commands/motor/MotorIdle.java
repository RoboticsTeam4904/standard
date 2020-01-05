package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Idles the motor (sets speed to 0).
 *
 */
public class MotorIdle implements Command {
	protected final Motor motor;

	/**
	 * Constructor.
	 *
	 * @param motor
	 */
	public MotorIdle(Motor motor) {
		super("MotorIdle");
		this.motor = motor;
		requires(motor);
		// setInterruptible(true); // default command
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
			LogKitten.d("MotorIdle ended");
		} else {
			LogKitten.d("MotorIdle interupted");
		}
	}

	// @Override
	// protected void interrupted()
	// {
	// LogKitten.d("MotorIdle interupted");
	// }
}
