package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class InjectedCommand extends CommandBase {
	private final CommandBase previous;

	public InjectedCommand(String name, CommandBase previous) {
		super();
		setName(name);
		this.previous = previous;
	}

	public InjectedCommand(CommandBase previous) {
		this("Injected(" + previous.getName() + ")", previous);
	}

	@Override
	public final void initialize() {
		if (previous != null && previous.isScheduled()) {
			previous.cancel();
		}
		onInitialize();
	}

	public final void interrupted() {
		onInterrupted();
		if (previous != null && !previous.isScheduled()) {
			previous.schedule();
		}
	}

	public final void end() {
		onEnd();
		if (previous != null && !previous.isScheduled()) {
			previous.schedule();
		}
	}

	protected abstract void onInitialize();

	protected abstract void onInterrupted();

	protected abstract void onEnd();
}
