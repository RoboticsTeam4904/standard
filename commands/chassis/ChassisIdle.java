package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.*; 

/**
 * This command causes the Chassis to idle by spawning a MotorIdle for every Motor in the Chassis.
 *
 */
public class ChassisIdle extends ParallelCommandGroup 
{
	/**
	 *
	 * @param chassis
	 *        The robot Chassis to idle.
	 */
	Chassis selfChassis;

	public ChassisIdle(Chassis chassis) 
	{
		this.selfChassis = chassis;
		Motor[] motors = chassis.getMotors();
		for (Motor motor : motors) 
		{
			addCommands(new MotorIdle(motor));
		}
	}

	@Override
	public Set<Subsystem> getRequirements() 
	{
		HashSet<Subsystem> set = new HashSet<Subsystem>();
		set.add(this.selfChassis);
		return set;
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {}
}
