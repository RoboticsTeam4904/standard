package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command causes the Chassis to idle by spawning a MotorIdle for every Motor in the Chassis.
 *
 */
public class ChassisIdle extends CommandGroup {
	/**
	 *
	 * @param chassis
	 *        The robot Chassis to idle.
	 */
	public ChassisIdle(Chassis chassis) {
		super("ChassisIdle");
		requires(chassis);
		setInterruptible(true); // default command
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) {
			addParallel(new MotorIdle(motor));
		}
	}
	
	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
	
	@Override
	protected boolean isFinished() {
		return false; // default command
	}
}
