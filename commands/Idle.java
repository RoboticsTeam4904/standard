package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An indefinite command that optionally takes a subsystem
 * and does absolutely nothing. If you do specify
 * a subsystem, it will interrupt other commands on that subsystem
 * following normal Scheduler behavior/functionality.
 * 
 * Potential Pitfall: Do not confuse this command with MotorIdle.
 * This command will do *absolutely nothing*, meaning that if a motor's
 * input value is already set, this command will not change that input,
 * leaving the motor running at its current value (potentially moving).
 * MotorIdle will set the motor's value to 0.
 */
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
