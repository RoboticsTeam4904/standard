package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public abstract class InjectedCommand extends Command {
	private final Command previous;
	
	public InjectedCommand(Command previous) {
		super();
		this.previous = previous;
	}
	
	public InjectedCommand(Command previous, String name) {
		super(name);
		this.previous = previous;
	}
	
	public InjectedCommand(Command previous, double timeout) {
		super(timeout);
		this.previous = previous;
	}
	
	public InjectedCommand(Command previous, String name, double timeout) {
		super(name, timeout);
		this.previous = previous;
	}
	
	@Override
	final protected void initialize() {
		if (!previous.isCanceled()) {
			previous.cancel();
		}
		onInitialize();
	}
	
	@Override
	final protected void interrupted() {
		onInterrupted();
		if (!previous.isRunning() || previous.isCanceled()) {
			previous.start();
		}
	}
	
	@Override
	final protected void end() {
		onEnd();
		if (!previous.isRunning() || previous.isCanceled()) {
			previous.start();
		}
	}
	
	abstract protected void onInitialize();
	
	abstract protected void onInterrupted();
	
	abstract protected void onEnd();
}
