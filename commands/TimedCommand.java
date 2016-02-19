package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Because the WPILib command timer
 * is only relative to the start of
 * the command, this class was
 * created to allow a timer to be
 * reset.
 *
 */
public abstract class TimedCommand extends Command {
	protected double lastReset;
	protected double interval;
	
	/**
	 * Constructor.
	 *
	 * @param name
	 */
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
		LogKitten.d(Boolean.toString(lastReset + interval > timeSinceInitialized()));
		return !(lastReset + interval > timeSinceInitialized());
	}
	
	/**
	 * Resets the timer
	 * This is public to allow other classes to reset timers
	 */
	public void resetTimer() {
		lastReset = timeSinceInitialized();
	}
}
