package org.usfirst.frc4904.standard.commands;

import java.util.Set;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunFor extends CommandBase {
	protected final double duration;
	protected final CommandBase command;
	protected boolean firstTick;

	/**
	 * Run a command for a given amount of time, in seconds. The command will be
	 * cancelled at the end. For example, if you want to go forward for 3 seconds,
	 * use: new RunFor(new GoForward(), 3)
	 *
	 * @param name          The name of this RunFor
	 * @param command       The command to be run for the duration
	 * @param duration      A duration in seconds
	 * @param interruptible Whether this command should be interruptible
	 */
	public RunFor(String name, CommandBase command, double duration) {
		setName(name);
		this.duration = duration;
		this.command = command;
		firstTick = true;
	}

	/**
	 * Run a command for a given amount of time, in seconds. The command will be
	 * cancelled at the end. For example, if you want to go forward for 3 seconds,
	 * use: new RunFor(new GoForward(), 3)
	 *
	 * @param command       The command to be run for the duration
	 * @param duration      A duration in seconds
	 * @param interruptible Whether this command should be interruptible
	 */
	public RunFor(CommandBase command, double duration) {
		this("RunFor[" + command.getName() + "]", command, duration);
	}

	public void initialize() {
		command.withTimeout(duration);
	}

	public void execute() {
	}

	public boolean isFinished() {
		if (firstTick) {
			firstTick = false;
			return isTimedOut(); // TODO: needs to be changed
		}
		return isTimedOut() || !command.isScheduled();
	}

	public void end() {
		command.cancel();
		firstTick = true;
	}

	public void interrupted() {
		end();
	}

	public Set<Subsystem> getRequirements() {
		return command.getRequirements();
	}
}
