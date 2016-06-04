package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public abstract class InjectedCommand extends Command {
	private final Command previous;
	
	public InjectedCommand(Command previous) {
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
	final protected void end() {
		finish();
		previous.start();
	}
	
	abstract protected void finish();
}
