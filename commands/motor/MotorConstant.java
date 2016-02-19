package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs a motor at a constant speed until interrupted.
 */
public class MotorConstant extends Command {
	protected final double motorSpeed;
	protected final Motor motor;
	
	/**
	 *
	 * @param motor
	 *        The motor to set the speed of.
	 * @param motorSpeed
	 *        The speed to set the motor to.
	 */
	public MotorConstant(Motor motor, double motorSpeed) {
		this.motor = motor;
		this.motorSpeed = motorSpeed;
		requires(motor);
		setInterruptible(true);
	}
	
	@Override
	protected void initialize() {
		motor.set(motorSpeed);
	}
	
	@Override
	protected void execute() {
		motor.set(motorSpeed);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
}
