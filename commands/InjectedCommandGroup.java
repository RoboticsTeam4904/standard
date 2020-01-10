package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;

public abstract class InjectedCommandGroup extends CommandGroupBase {
	private final CommandGroupBase previous;

	public InjectedCommandGroup(CommandGroupBase previous) {
		super();
		this.previous = previous;
	}

	public InjectedCommandGroup(String name, CommandGroupBase previous) {
		super();
		setName(name);
		this.previous = previous;
	}

	final public void initialize() {
		if (previous != null && (previous.isScheduled())) {
			previous.cancel();
		}
		onInitialize();
	}

	final protected void interrupted() {
		onInterrupted();
		if (previous != null && (!previous.isScheduled())) {
			previous.schedule();
		}
	}

	final protected void end() {
		onEnd();
		if (previous != null && (!previous.isScheduled())) {
			previous.schedule();
		}
	}

	abstract protected void onInitialize();

	abstract protected void onInterrupted();

	abstract protected void onEnd();
}
