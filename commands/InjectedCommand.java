package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.commands.CustomCommand;

public abstract class InjectedCommand extends CustomCommand {
	private final CustomCommand previous;
	
	public InjectedCommand(String name, double timeout, CustomCommand previous) {
		super(name, timeout);
		this.previous = previous;
	}

	public InjectedCommand(double timeout, CustomCommand previous) {
		this("Injected(" + previous.getName() + ")", timeout, previous);
	}

	public InjectedCommand(String name, CustomCommand previous) {
		super(name);
		this.previous = previous;
	}

	public InjectedCommand(CustomCommand previous) {
		this("Injected(" + previous.getName() + ")", previous);
	}
	
	@Override
	final public void initialize() {
		if (previous != null && (previous.isRunning())) {
			previous.cancel();
		}
		onInitialize();
	}
	
	final public void interrupted() {
		onInterrupted();
		if (previous != null && (!previous.isRunning())) {
			previous.schedule();
		}
	}
	
	final public void end() {
		onEnd();
		if (previous != null && (!previous.isRunning())) {
			previous.schedule();
		}
	}
	
	abstract protected void onInitialize();
	
	abstract protected void onInterrupted();
	
	abstract protected void onEnd();
}
