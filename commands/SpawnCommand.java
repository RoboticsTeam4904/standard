package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public class SpawnCommand extends Command {
	private final Command childCommand;
	
	public SpawnCommand(Command childCommand) {
		super("SpawnCommand[" + childCommand.getName() + "]");
		this.childCommand = childCommand;
	}
	
	@Override
	protected void initialize() {
		childCommand.start();
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
