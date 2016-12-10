package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.command.Command;

/**
 * Abstract Command to allow for a reset function. This should
 * be used when a Health Check might need to reset a Command.
 */
public abstract class HealthProtectCommand extends Command {
	/**
	 * This should reset the command
	 */
	public abstract void reset();
}
