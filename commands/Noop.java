package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is a placeholder. It does not
 * cause anything to happen. Use it as a placeholder
 * when a Command object is needed.
 */
public class Noop extends Command {
	public Noop() {
		super("Noop");
	}
	
	@Override
	protected void initialize() {}
	
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
