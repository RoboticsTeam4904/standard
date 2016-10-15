package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * An indefinite command that sets the given motor's
 * input to 0, effectively idling it.
 */
public class MotorIdle extends Command {
	protected final Motor motor;
	
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
