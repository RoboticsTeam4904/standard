package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command causes the chassis
 * to idle. It does this by calling
 * idle commands on each of the
 * motors in the chassis.
 *
 */
public class ChassisIdle extends CommandGroup {
	/**
	 *
	 * @param chassis
	 *        The robot chassis to idle.
	 */
	public ChassisIdle(Chassis chassis) {
		super("ChassisIdle");
		requires(chassis);
		LogKitten.v("ChassisIdle created for " + Integer.toString(chassis.getNumberMotors()) + " motors");
		setInterruptible(true); // default command
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) {
			addParallel(new MotorIdle(motor));
		}
	}
	
	@Override
	protected void initialize() {
		LogKitten.v("ChassisIdle initialized");
	}
	
	@Override
	protected void execute() {
		LogKitten.d("ChassisIdle executing");
	}
	
	@Override
	protected void end() {
		LogKitten.v("ChassisIdle ended");
	}
	
	@Override
	protected void interrupted() {
		LogKitten.w("ChassisIdle interrupted");
	}
	
	@Override
	protected boolean isFinished() {
		return false; // default command
	}
}
