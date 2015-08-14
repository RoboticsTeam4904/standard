package org.usfirst.frc4904.cmdbased.commands.motor;


import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorIdle extends Command {
	private final SpeedController motor;
	private final LogKitten logger;
	
	public <A extends Subsystem & SpeedController> MotorIdle(A motor) {
		super("MotorIdle");
		this.motor = motor;
		requires(motor);
		setInterruptible(true); // default command
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_ERROR);
		logger.v("MotorIdle created");
	}
	
	protected void initialize() {
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
