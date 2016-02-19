package org.usfirst.frc4904.standard.commands;


import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RunIfElse extends Command {
	private final Command ifCommand;
	private final Command elseCommand;
	private final BooleanSupplier condition;
	
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
	protected void end() {}
	
	@Override
	protected void interrupted() {
		ifCommand.cancel();
		elseCommand.cancel();
	}
}
