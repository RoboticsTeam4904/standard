package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public abstract class TimedCommand extends Command {
	private double lastReset;
	private double interval;
	
	public TimedCommand(String name) {
		super(name);
		lastReset = 0.0;
	}
	
	/**
	 * Sets the interval between reset and getTimed
	 * 
	 * @param interval
	 */
	protected void setTimeInterval(double interval) {
		this.interval = interval;
	}
	
	/**
	 * Returns true if there has been an
	 * interval of time since the last reset
	 * 
	 * @return
	 */
	protected boolean getTimed() {
		if (lastReset + interval > timeSinceInitialized()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Resets the timer
	 * This is public to allow other classes to reset timers
	 */
	public void resetTimer() {
		lastReset = timeSinceInitialized();
	}
}
