package org.usfirst.frc4904.cmdbased.commands.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisShift extends Command {
	private SolenoidShifters solenoids;
	
	public ChassisShift(String name, SolenoidShifters solenoids) {
		super(name);
		this.solenoids = solenoids;
		requires(solenoids);
		setInterruptible(false);
	}
	
	protected void initialize() {
		solenoids.shift();
	}
	
	protected void execute() {}
	
	protected void interrupted() {}
	
	protected void end() {}
	
	protected boolean isFinished() {
		return false;
	}
}
