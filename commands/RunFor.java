package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RunFor extends CommandGroup {
	protected final double duration;
	protected final Command command;
	
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
		setTimeout(duration);
		addSequential(command);
	}
	
	@Override
	protected boolean isFinished() {
		return isTimedOut() || !command.isRunning();
	}
}
