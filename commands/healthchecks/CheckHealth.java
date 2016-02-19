package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * CheckHealth runs a series of health checks.
 * It also includes a reset for all of the health checks.
 */
public class CheckHealth extends CommandGroup {
	protected final AbstractHealthCheck[] commands;
	
	/**
	 * CheckHealth runs a series of health checks.
	 * It also includes a reset for all of the health checks.
	 *
	 * @param command
	 *        The health check commands to run
	 */
	public CheckHealth(AbstractHealthCheck... command) {
		commands = command;
		for (Command c : commands) {
			addParallel(c);
		}
		setRunWhenDisabled(true);
	}
	
	/**
	 * Resets all of the health checks.
	 * Should be run on disable.
	 */
	public void reset() {
		for (AbstractHealthCheck c : commands) {
			c.reset();
		}
	}
	
	/**
	 * Returns current health status.
	 *
	 * @return
	 */
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
