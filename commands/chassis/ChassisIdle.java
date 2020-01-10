package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * 
 * This command causes the Chassis to idle by spawning a MotorIdle for every
 * Motor in the Chassis.
 * 
 */
public class ChassisIdle extends ParallelCommandGroup {
	/**
	 * 
	 * @param chassis The robot Chassis to idle.
	 * 
	 */

	public ChassisIdle(Chassis chassis, String name) {
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) {
			addCommands(new MotorIdle(motor));
		}
		setName(name);
		addRequirements(chassis);
	}

	public ChassisIdle(Chassis chassis) {
		this(chassis, "Chassis Idle");
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupt) {
	}
}
