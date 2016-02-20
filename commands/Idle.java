package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Idle extends Command {
	public Idle() {
		super("Idle(No Subsystem)");
		setInterruptible(true);
	}
	
	public Idle(Subsystem subsystem) {
		super("Idle[" + subsystem.getName() + "]");
		setInterruptible(true);
		requires(subsystem);
	}
	
	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
}
