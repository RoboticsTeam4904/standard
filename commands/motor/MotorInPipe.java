package org.usfirst.frc4904.cmdbased.commands.motor;


import org.usfirst.frc4904.cmdbased.InPipable;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorInPipe extends Command {
	private final SpeedController motor;
	private final LogKitten logger;
	private final InPipable source;
	
	public <A extends Subsystem & SpeedController> MotorInPipe(A motor, InPipable source) {
		super("MotorInPipe");
		this.motor = motor;
		this.source = source;
		requires(motor);
		setInterruptible(true);
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_VERBOSE);
		logger.v("MotorInPipe created for " + motor.getName());
	}
	
	protected void initialize() {
		logger.v("MotorInPipe initialized");
	}
	
	protected void execute() {
		motor.set(source.readPipe()[0]);
	}
	
	protected void end() {}
	
	protected void interrupted() {}
	
	protected boolean isFinished() {
		return false;
	}
}