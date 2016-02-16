package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

public class Cancel extends Command {
	protected final Command command;
	
	public Cancel(Command command) {
		super("Cancel[" + command + "]");
		setInterruptible(false);
		this.command = command;
	}
	
	@Override
	protected void initialize() {
		LogKitten.v("Initializing " + getName());
		command.cancel();
	}
	
	@Override
	protected void execute() {}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
}
