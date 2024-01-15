package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.Command;

public abstract class InjectedCommand extends Command {
	private final Command previous;

	public InjectedCommand(String name, Command previous) {
		super();
		setName(name);
		this.previous = previous;
	}

	public InjectedCommand(Command previous) {
		this("Injected(" + previous.getName() + ")", previous);
	}

	@Override
	public final void initialize() {
		if (previous != null && previous.isScheduled()) {
			previous.cancel();
		}
		onInitialize();
	}

	@Override
	public final void end(boolean interrupted) {
		if (interrupted) {
			onInterrupted();
		} else {
			onEnd();
		}
		if (previous != null && !previous.isScheduled()) {
			previous.schedule();
		}
	}

	protected abstract void onInitialize();

	protected abstract void onInterrupted();

	protected abstract void onEnd();
}
