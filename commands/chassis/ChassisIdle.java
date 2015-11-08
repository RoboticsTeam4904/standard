package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChassisIdle extends CommandGroup {
	private final Chassis chassis;
	
	public ChassisIdle(Chassis chassis) {
		super("ChassisIdle");
		this.chassis = chassis;
		requires(chassis);
		LogKitten.v("ChassisIdle created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
		setInterruptible(true); // default command
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) {
			addParallel(new MotorIdle(motor));
		}
	}
	
	protected void initialize() {
		LogKitten.v("ChassisIdle initialized");
	}
	
	protected void execute() {
		LogKitten.d("ChassisIdle executing");
	}
	
	protected void end() {
		LogKitten.v("ChassisIdle ended");
	}
	
	protected void interrupted() {
		LogKitten.w("ChassisIdle interrupted");
	}
	
	protected boolean isFinished() {
		return false; // default command
	}
}
