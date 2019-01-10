package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj.command.Command;

public class WaitFor extends Command {
	/**
	 * A command that waits for a given amount of time, using setTimeout() to
	 * continue with the command queue.
	 *
	 * @param duration
	 *        A duration in seconds
	 */
	public WaitFor(double duration) {
		super("WaitFor[" + duration + "]");
		setTimeout(duration);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {
		end();
	}
}

