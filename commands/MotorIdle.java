package org.usfirst.frc4904.cmdbased.commands;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

public class MotorIdle extends Command {
	private final Motor motor;
	private final LogKitten logger;
	
	public MotorIdle(Motor motor) {
		super("MotorIdle");
		this.motor = motor;
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_ERROR);
		logger.v("MotorIdle created");
	}
	
	protected void initialize() {
		requires(motor);
		setInterruptible(true); // default command
		motor.set(0);
		logger.v("MotorIdle initialized");
	}
	
	protected void execute() {
		motor.set(0);
		logger.d("MotorIdle executing");
	}
	
	protected void end() {
		logger.v("MotorIdle ended");
	}
	
	protected void interrupted() {
		logger.w("MotorIdle interupted");
	}
	
	protected boolean isFinished() {
		return false; // default command
	}
}
