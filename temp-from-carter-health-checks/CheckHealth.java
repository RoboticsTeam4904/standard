package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CheckHealth extends CommandGroup {
	private final AbstractHealthcheck[] commands;
	
	public CheckHealth(AbstractHealthcheck... command) {
		commands = command;
		for (Command c : commands) {
			addParallel(c);
		}
		setRunWhenDisabled(true);
	}
	
	public void reset() {
		for (AbstractHealthcheck c : commands) {
			c.reset();
		}
	}
	
	public HealthStatus getStatus() {
		HealthStatus status = HealthStatus.UNCERTAIN;
		for (AbstractHealthcheck c : commands) {
			if (c.getStatus().compareTo(status) > 0) {
				status = c.getStatus();
			}
		}
		return status;
	}
}