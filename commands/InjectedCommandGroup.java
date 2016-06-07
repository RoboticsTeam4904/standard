package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class InjectedCommandGroup extends CommandGroup {
	private final Command previous;
	
	public InjectedCommandGroup(Command previous) {
		super();
		this.previous = previous;
	}
	
	public InjectedCommandGroup(String name, Command previous) {
		super(name);
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
