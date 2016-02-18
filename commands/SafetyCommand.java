package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command that will only run if the isSafe() method returns safe.
 * To use, implement an isSafe() method,
 * and use initializeIfSafe() in place of the initialize() method
 */
public abstract class SafetyCommand extends Command {
	protected String reasonUnsafe;
	
	@Override
	protected final void initialize() {
		if (isSafe()) {
			initializeIfSafe();
			return;
		}
		this.cancel();
		if (reasonUnsafe == null) {
			reasonUnsafe = "the required safety conditions haven't been met";
		}
		LogKitten.e("Command " + getName() + " cannot run because " + reasonUnsafe + ". Cancelling...");
	}
	
	protected void setUnsafeReason(String reasonUnsafe) {
		this.reasonUnsafe = reasonUnsafe;
	}
	
	/**
	 * The initialize method is called the first time this Command is run after being started,
	 * on the condition that isSafe() returns true.
	 */
	protected abstract void initializeIfSafe();
	
	/**
	 * Determines if the command is safe to run.
	 * setUnsafeReason(String) should be called to describe a safety failure.
	 * 
	 * @return is the command safe to run?
	 */
	protected abstract boolean isSafe();
}
