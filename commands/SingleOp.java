package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

/**
 * Framework for providing easy extension of Command
 * when an anonymous inner command that will run once
 * is desired.
 *
 */
public abstract class SingleOp extends Command {
	/**
	 * Will run on initialize()
	 */
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
