package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public class BranchCommand extends Command {
	private final Command ifCommand;
	private final Command elseCommand;
	private final BooleanInterface condition;
	
	public BranchCommand(Command ifCommand, Command elseCommand, BooleanInterface condition) {
		super("BranchIf[" + ifCommand.getName() + "]Else[" + elseCommand.getName() + "]");
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.condition = condition;
	}
	
	@Override
	protected void initialize() {
		if (condition.evaluate()) {
			ifCommand.start();
		} else {
			elseCommand.start();
		}
	}
	
	@Override
	protected void execute() {}
	
	@Override
	protected boolean isFinished() {
		return !ifCommand.isRunning() && !elseCommand.isRunning();
	}
	
	@Override
	protected void end() {
		ifCommand.cancel();
		elseCommand.cancel();
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
