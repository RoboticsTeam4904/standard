package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Idles motors of a subsystem.
 * Used as a default command
 *
 */
public class IdleMotors extends CommandGroup{

	/**
	 * Run MotorIdle (from WPILib) in parallel on every motor
	 * from a list of motors with unrestricted size.
	 *
	 * @param subsystem
	 * @param motors
	 */
	public IdleMotors(Subsystem subsystem, Motor... motors) {
		super("IdleMotors");
		requires(subsystem);
		for (Motor motor : motors) {
			requires(motor);
			addParallel(new MotorIdle(motor));
		}
	}
}
