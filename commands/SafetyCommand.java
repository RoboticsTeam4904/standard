package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command that will only run if the isSafe() method returns safe.
 * To use, implement an isSafe() method,
 * and use initializeIfSafe() in place of the initialize() method
 */
public abstract class SafetyCommand extends Command {
	@Override
	protected final void initialize() {
		if (!isSafe()) {
			this.cancel();
			LogKitten.e("Command " + getName() + " isn't safe to run! Cancelling...");
			return;
		}
		initializeIfSafe();
	}
	
	/**
	 * The initialize method is called the first time this Command is run after being started,
	 * on the condition that isSafe() returns true.
	 */
	protected abstract void initializeIfSafe();
	
	/**
	 * Determines if the command is safe to run.
	 * 
	 * @return is the command safe to run?
	 */
	protected abstract boolean isSafe();
}
