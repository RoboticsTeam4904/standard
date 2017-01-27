package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * GuardCommand takes in two commands, a background and a primary.
 * When GuardCommand is started, both commands will start in parallel.
 * However, when the primary command ends, the background command will be cancelled
 * and the GuardCommand instance will end as well.
 * This is especially useful when a two commands need to be run in parallel,
 * but one of them (the background command) runs indefinitely. Using GuardCommand, that
 * background command will be run for the duration of the primary command, or for less time
 * if it ends by itself. Note that in the latter case, the primary command will finish independently.
 *
 */
public class GuardCommand extends Command {
	protected final Command backgroundCommand;
	protected final Command primaryCommand;
	private boolean hasRunOnce = false;

	public GuardCommand(Command backgroundCommand, Command primaryCommand) {
		super();
		this.backgroundCommand = backgroundCommand;
		this.primaryCommand = primaryCommand;
	}

	@Override
	public synchronized boolean doesRequire(Subsystem system) {
		return backgroundCommand.doesRequire(system) || primaryCommand.doesRequire(system);
	}

	@Override
	protected void initialize() {
		backgroundCommand.start();
		primaryCommand.start();
		hasRunOnce = false;
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		if (primaryCommand.isRunning() && !hasRunOnce) {
			hasRunOnce = true;
		}
		return !primaryCommand.isRunning() && hasRunOnce;
	}

	@Override
	protected void end() {
		backgroundCommand.cancel();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
