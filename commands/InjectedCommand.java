package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public abstract class InjectedCommand extends Command {
	private final Command previous;
	
	public InjectedCommand(Command previous) {
		super();
		this.previous = previous;
	}
	
	public InjectedCommand(String name, Command previous) {
		super(name);
		this.previous = previous;
	}
	
	public InjectedCommand(double timeout, Command previous) {
		super(timeout);
		this.previous = previous;
	}
	
	public InjectedCommand(String name, double timeout, Command previous) {
		super(name, timeout);
		this.previous = previous;
	}
	
	@Override
	final protected void initialize() {
		if (previous != null && (previous.isRunning() || !previous.isCanceled())) {
			previous.cancel();
		}
		onInitialize();
	}
	
	@Override
	final protected void interrupted() {
		onInterrupted();
		if (previous != null && (!previous.isRunning() || previous.isCanceled())) {
			previous.start();
		}
	}
	
	@Override
	final protected void end() {
		onEnd();
		if (previous != null && (!previous.isRunning() || previous.isCanceled())) {
			previous.start();
		}
	}
	
	abstract protected void onInitialize();
	
	abstract protected void onInterrupted();
	
	abstract protected void onEnd();
}
