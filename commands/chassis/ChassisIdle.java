package org.usfirst.frc4904.cmdbased.commands.chassis;


import org.usfirst.frc4904.cmdbased.commands.motor.MotorIdle;
import org.usfirst.frc4904.cmdbased.subsystems.chassis.Chassis;
import org.usfirst.frc4904.cmdbased.subsystems.motor.Motor;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChassisIdle extends CommandGroup {
	private final Chassis chassis;
	private final LogKitten logger;
	
	public ChassisIdle(Chassis chassis) {
		super("ChassisIdle");
		this.chassis = chassis;
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_ERROR);
		logger.v("ChassisIdle created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
		setInterruptible(true); // default command
	}
	
	protected void initialize() {
		requires(chassis);
		logger.v("ChassisIdle initialized");
	}
	
	protected void execute() {
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) {
			addParallel(new MotorIdle(motor));
		}
		logger.d("ChassisIdle executing");
		logger.d("Chassis has " + Integer.toString(motors.length) + " motors");
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
