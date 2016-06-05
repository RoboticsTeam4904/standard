package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class InjectedCommandGroup extends CommandGroup {
	private final Command previous;
	
	public InjectedCommandGroup(Command previous) {
		super();
		this.previous = previous;
	}
	
	public InjectedCommandGroup(Command previous, String name) {
		super(name);
		this.previous = previous;
	}
	
	@Override
	final protected void interrupted() {
		onInterrupted();
		if (!previous.isRunning()) {
			previous.start();
		}
	}
	
	@Override
	final protected void end() {
		onEnd();
		if (!previous.isRunning()) {
			previous.start();
		}
	}
	
	abstract protected void onInterrupted();
	
	abstract protected void onEnd();
}
