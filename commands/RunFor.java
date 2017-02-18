package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public class RunFor extends Command {
	protected final double duration;
	protected final Command command;
	protected boolean firstTick;

	/**
	 * Run a command for a given amount of time, in seconds. The command will be cancelled at the end.
	 * For example, if you want to go forward for 3 seconds, use:
	 * new RunFor(new GoForward(), 3)
	 *
	 * @param command
	 *        The command to be run for the duration
	 * @param duration
	 *        A duration in seconds
	 * @param interruptible
	 *        Whether this command should be interruptible
	 */
	public RunFor(Command command, double duration) {
		super("RunFor[" + command.getName() + "]");
		this.duration = duration;
		this.command = command;
		firstTick = true;
		setTimeout(duration);
	}

	@Override
	protected void initialize() {
		command.start();
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		if (firstTick) {
			firstTick = false;
			return isTimedOut();
		}
		return isTimedOut() || !command.isRunning();
	}

	@Override
	protected void end() {
		command.cancel();
		firstTick = true;
	}

	@Override
	protected void interrupted() {
		end();
	}
}
