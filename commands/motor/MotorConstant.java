package org.usfirst.frc4904.standard.commands.motor;

import java.util.HashSet;
import java.util.Set;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Runs a motor at a constant speed until interrupted.
 */
public class MotorConstant implements Command {
	protected final double motorSpeed;
	protected final Motor motor;

	/**
	 * @param name       The name of this command.
	 * @param motor      The motor to set the speed of.
	 * @param motorSpeed The speed to set the motor to.
	 */
	public MotorConstant(String name, Motor motor, double motorSpeed) {
		this.motor = motor;
		this.motorSpeed = motorSpeed;
	}

	/**
	 *
	 * @param motor      The motor to set the speed of.
	 * @param motorSpeed The speed to set the motor to.
	 */
	public MotorConstant(Motor motor, double motorSpeed) {
		this("MotorConstant", motor, motorSpeed);
	}

	@Override
	public void initialize() {
		motor.set(motorSpeed);
	}

	@Override
	public void execute() {
		motor.set(motorSpeed);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		if (!interrupted) {
			motor.set(0.0);
		}
	}

	@Override
	public Set<Subsystem> getRequirements() {
		Set<Subsystem> motors = new HashSet<Subsystem>();
		motors.add(motor);
		return motors;
	}
}
