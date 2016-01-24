package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CheckHealth extends CommandGroup {
	private final AbstractHealthCheck[] commands;
	
	public CheckHealth(AbstractHealthCheck... command) {
		commands = command;
		for (Command c : commands) {
			addParallel(c);
		}
		setRunWhenDisabled(true);
	}
	
	public void reset() {
		for (AbstractHealthCheck c : commands) {
			c.reset();
		}
	}
	
	public HealthLevel getStatus() {
		HealthLevel status = HealthLevel.UNKNOWN;
		for (AbstractHealthCheck c : commands) {
			if (c.getStatus().compareTo(status) > 0) {
				status = c.getStatus();
			}
		}
		return status;
	}
}
