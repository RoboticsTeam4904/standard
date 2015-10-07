package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChassisIdle extends CommandGroup {
	private final Chassis chassis;
	private final LogKitten logger;
	
	public ChassisIdle(Chassis chassis) {
		super("ChassisIdle");
		this.chassis = chassis;
		requires(chassis);
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_ERROR);
		logger.v("ChassisIdle created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
		setInterruptible(true); // default command
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) {
			addParallel(new MotorIdle(motor));
		}
	}
	
	protected void initialize() {
		logger.v("ChassisIdle initialized");
	}
	
	protected void execute() {
		logger.d("ChassisIdle executing");
	}
	
	protected void end() {
		logger.v("ChassisIdle ended");
	}
	
	protected void interrupted() {
		logger.w("ChassisIdle interrupted");
	}
	
	protected boolean isFinished() {
		return false; // default command
	}
}
