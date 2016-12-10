package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.command.Command;

/**
 * Abstract Command to allow for a reset function
 */
public abstract class HealthProtectCommand extends Command {
	/**
	 * This should reset the command
	 */
	public abstract void reset();
}
