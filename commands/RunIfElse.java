package org.usfirst.frc4904.standard.commands;


import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This command should be used if one of two commands should be run,
 * for at time of initialization exactly which one to run is not known.
 */
public class RunIfElse extends Command {
	protected final Command ifCommand;
	protected final Command elseCommand;
	protected Command runningCommand;
	protected final BooleanSupplier[] booleanSuppliers;
	protected boolean hasRunOnce;
	
	/**
	 * @param ifCommand
	 *        Will run if a true is supplied
	 * @param elseCommand
	 *        Will run if a false is supplied
	 */
	public RunIfElse(Command ifCommand, Command elseCommand, BooleanSupplier... booleanSuppliers) {
		super("RunIf[" + ifCommand.getName() + "]Else[" + elseCommand.getName() + "]");
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.booleanSuppliers = booleanSuppliers;
	}
	
	/**
	 * @param ifCommand
	 *        Will run if a true is supplied
	 * @param elseCommand
	 *        Will run if a false is supplied
	 */
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
