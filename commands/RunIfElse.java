package org.usfirst.frc4904.standard.commands;


import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RunIfElse extends Command {
	protected final Command ifCommand;
	protected final Command elseCommand;
	protected Command runningCommand;
	protected final BooleanSupplier condition;
	protected boolean hasRunOnce;
	
	public RunIfElse(Command ifCommand, Command elseCommand, BooleanSupplier condition) {
		super("RunIf[" + ifCommand.getName() + "]Else[" + elseCommand.getName() + "]");
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.condition = condition;
	}
	
	@Override
	public boolean doesRequire(Subsystem subsystem) {
		return ifCommand.doesRequire(subsystem) || elseCommand.doesRequire(subsystem);
	}
	
	@Override
	protected void initialize() {
		if (condition.getAsBoolean()) {
			ifCommand.start();
			runningCommand = ifCommand;
		} else {
			elseCommand.start();
			runningCommand = elseCommand;
		}
	}
	
	@Override
	protected void execute() {}
	
	@Override
	protected boolean isFinished() {
		if (runningCommand.isRunning() && !hasRunOnce) {
			hasRunOnce = true;
		}
		return !runningCommand.isRunning() && hasRunOnce;
	}
	
	@Override
	protected void end() {
		ifCommand.cancel();
		elseCommand.cancel();
		hasRunOnce = false;
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
