package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class InjectedCommand extends CommandBase {
	private final CustomCommand previous;
	
	public InjectedCommand(String name, double timeout, CustomCommand previous) {
		super();
		this.withTimeout(timeout);
		setName(name);
		this.previous = previous;
	}

	public InjectedCommand(double timeout, CustomCommand previous) {
		this("Injected(" + previous.getName() + ")", timeout, previous);
	}

	public InjectedCommand(String name, CustomCommand previous) {
		super();
		setName(name);
		this.previous = previous;
	}

	public InjectedCommand(CustomCommand previous) {
		this("Injected(" + previous.getName() + ")", previous);
	}
	
	@Override
	final public void initialize() {
		if (previous != null && (previous.isScheduled())) {
			previous.cancel();
		}
		onInitialize();
	}
	
	final public void interrupted() {
		onInterrupted();
		if (previous != null && (!previous.isScheduled())) {
			previous.schedule();
		}
	}
	
	final public void end() {
		onEnd();
		if (previous != null && (!previous.isScheduled())) {
			previous.schedule();
		}
	}
	
	abstract protected void onInitialize();
	
	abstract protected void onInterrupted();
	
	abstract protected void onEnd();
}
