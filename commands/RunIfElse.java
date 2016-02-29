package org.usfirst.frc4904.standard.commands;


import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RunIfElse extends Command {
	protected final Command ifCommand;
	protected final Command elseCommand;
	protected Command runningCommand;
	protected final BooleanSupplier[] booleanSuppliers;
	protected boolean hasRunOnce;
	
	public RunIfElse(Command ifCommand, Command elseCommand, BooleanSupplier... booleanSuppliers) {
		super("RunIf[" + ifCommand.getName() + "]Else[" + elseCommand.getName() + "]");
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.booleanSuppliers = booleanSuppliers;
	}
	
	protected RunIfElse(String name, Command ifCommand, Command elseCommand, BooleanSupplier... booleanSuppliers) {
		super(name);
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.booleanSuppliers = booleanSuppliers;
	}
	
	@Override
	public boolean doesRequire(Subsystem subsystem) {
		return ifCommand.doesRequire(subsystem) || elseCommand.doesRequire(subsystem);
	}
	
	@Override
	protected void initialize() {
		for (BooleanSupplier booleanSupplier : booleanSuppliers) {
			if (!booleanSupplier.getAsBoolean()) {
				elseCommand.start();
				runningCommand = elseCommand;
				return;
			}
		}
		ifCommand.start();
		runningCommand = ifCommand;
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
