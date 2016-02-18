package org.usfirst.frc4904.standard.commands;


import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RunIf extends Command {
	protected final Command command;
	protected final BooleanSupplier[] booleanProviders;
	
	/**
	 * Run a command based on a conditional callback.
	 * For example, if you only want to shoot if a shooter is ready (based on its isReady() function), use:
	 * new RunIf(new Shoot(), shooter::isReady)
	 * This double-colon syntax only works in Java 8. If you must use this with an earlier version of Java, use:
	 * new RunIf(new Shoot(), new BooleanInterface() { boolean evaluate() { return shooter.isReady(); } })
	 * 
	 * @param command
	 *        The command to be run if the condition is met
	 * @param bi
	 *        A condition function using Java 8's colon syntax
	 */
	public RunIf(Command command, BooleanSupplier... booleanSuppliers) {
		super("RunIf[" + command.getName() + "]");
		this.command = command;
		this.booleanProviders = booleanSuppliers;
	}
	
	@Override
	public boolean doesRequire(Subsystem subsystem) {
		return command.doesRequire(subsystem);
	}
	
	@Override
	protected void initialize() {
		for (BooleanSupplier booleanProvider : booleanProviders) {
			if (!booleanProvider.getAsBoolean()) {
				return;
			}
		}
		command.start();
	}
	
	@Override
	protected boolean isFinished() {
		return !command.isRunning();
	}
	
	@Override
	protected void interrupted() {
		command.cancel();
	}
	
	@Override
	protected void execute() {}
	
	@Override
	protected void end() {}
}
