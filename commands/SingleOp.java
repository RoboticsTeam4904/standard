package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public abstract class SingleOp extends Command {
	abstract protected void runOp();
	
	@Override
	protected void initialize() {
		runOp();
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
