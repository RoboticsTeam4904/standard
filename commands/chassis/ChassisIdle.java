package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>
 * This command causes the Chassis to idle by spawning a MotorIdle for every
 * Motor in the Chassis.
 * </p>
 */
public class ChassisIdle extends ParallelCommandGroup {
	/**
	 * <p>
	 * @param chassis The robot Chassis to idle.
	 * </p>
	 */
	private Chassis chassis;

	public ChassisIdle(Chassis chassis) {
		this.chassis = chassis;
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) {
			addCommands(new MotorIdle(motor));
		}
	}

	@Override
	public Set<Subsystem> getRequirements() { return Set.of(chassis); }

	@Override
	public void initialize() {}

	@Override
	public void execute() {}

	@Override
	public void end(boolean interrupt) {}
}
