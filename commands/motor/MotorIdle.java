package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Idles the motor (sets speed to 0).
 *
 */
public class MotorIdle extends Command {
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
		setInterruptible(true); // default command
		LogKitten.d("MotorIdle created");
	}
	
	@Override
	protected void initialize() {
		motor.set(0);
		LogKitten.d("MotorIdle initialized");
	}
	
	@Override
	protected void execute() {
		motor.set(0);
		LogKitten.d("MotorIdle executing");
	}
	
	@Override
	protected boolean isFinished() {
		return false; // default command
	}
	
	@Override
	protected void end() {
		LogKitten.d("MotorIdle ended");
	}
	
	@Override
	protected void interrupted() {
		LogKitten.d("MotorIdle interupted");
	}
}
