package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * 
 * This command causes the Chassis to idle by spawning a MotorIdle for every
 * Motor in the Chassis.
 * 
 */
public class ChassisIdle extends ParallelCommandGroup {
	/**
	 * 
	 * @param name
	 * @param chassis The robot Chassis to idle.
	 */
	public ChassisIdle(String name, Chassis chassis) {
		for (Motor motor : chassis.getMotors()) {
			addCommands((Command) new MotorIdle(motor));
		}

		setName(name);
		addRequirements(chassis);
	}

	/**
	 * 
	 * @param chassis The robot Chassis to idle.
	 */
	public ChassisIdle(Chassis chassis) {
		this("ChassisIdle", chassis);
	}
}
