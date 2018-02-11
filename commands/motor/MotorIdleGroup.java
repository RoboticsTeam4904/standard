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
public class MotorIdleGroup extends CommandGroup{
	
	/**
	 * Run MotorIdle (from WPILib) in parallel on every motor
	 * from a list of motors with unrestricted size.
	 * 
	 * @param name
	 * @param subsystem
	 * @param motors
	 */
	public MotorIdleGroup(String name, Subsystem subsystem, Motor... motors) {
		super(name);
		requires(subsystem);
		for (Motor motor : motors) {
			requires(motor);
			addParallel(new MotorIdle(motor));
		}
	}

	/**
	 * Run MotorIdle (from WPILib) in parallel on every motor
	 * from a list of motors with unrestricted size.
	 * Logs the names of all motors that are being idled
	 *
	 * @param subsystem
	 * @param motors
	 */
	public MotorIdleGroup(Subsystem subsystem, Motor... motors) {
		this(MotorIdleGroup.makeName(motors), subsystem, motors);
	}

	public static String makeName(Motor... motors) {
		String name = "";
		for (Motor motor : motors) {
			if (motor.getName() != null) {
				name += motor.getName() + " ";
			} else {
				name += "unnamed motor ";
			}
			name += "idling";
		}
		return name;
	}
}
	