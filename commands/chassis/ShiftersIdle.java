package org.usfirst.frc4904.cmdbased.commands.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj.command.Command;

public class ShiftersIdle extends Command {
	// GNDN, but lets the shifters idle
	public ShiftersIdle(SolenoidShifters shifter) {
		requires(shifter);
		setInterruptible(true);
	}
	
	protected void initialize() {}
	
	protected void execute() {}
	
	protected void interrupted() {}
	
	protected void end() {}
	
	protected boolean isFinished() {
		return false;
	}
}
