package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;

public abstract class InjectedCommandGroup extends CommandGroupBase {
	private final CommandGroupBase previous;

	public InjectedCommandGroup(String name, CommandGroupBase previous) {
		super();
		setName(name);
		this.previous = previous;
	}

	public InjectedCommandGroup(CommandGroupBase previous) {
		this("InjectedCommandGroup", previous);
	}

	public final void initialize() {
		if (previous != null && previous.isScheduled()) {
			previous.cancel();
		}
		onInitialize();
	}

	protected final void interrupted() {
		onInterrupted();
		if (previous != null && !previous.isScheduled()) {
			previous.schedule();
		}
	}

	protected final void end() {
		onEnd();
		if (previous != null && !previous.isScheduled()) {
			previous.schedule();
		}
	}

	protected abstract void onInitialize();

	protected abstract void onInterrupted();

	protected abstract void onEnd();
}
