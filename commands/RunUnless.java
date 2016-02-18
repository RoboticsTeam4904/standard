package org.usfirst.frc4904.standard.commands;


import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RunUnless extends CommandGroup {
	protected final Command command;
	protected final BooleanSupplier[] booleanSuppliers;
	
	/**
	 * Run a command based on a conditional callback.
	 * For example, if you only want to shoot if a shooter is safe (based on its isUnsafe() function), use:
	 * new RunIf(new Shoot(), shooter::isUnsafe)
	 * This double-colon syntax only works in Java 8. If you must use this with an earlier version of Java, use:
	 * new RunIf(new Shoot(), new BooleanInterface() { boolean evaluate() { return shooter.isUnsafe(); } })
	 * 
	 * @param command
	 *        The command to be run if the condition is NOT met
	 * @param bi
	 *        A condition function using Java 8's colon syntax (will run unless the condition is true)
	 */
	public RunUnless(Command command, BooleanSupplier... booleanSuppliers) {
		super("RunUnless[" + command.getName() + "]");
		this.command = command;
		this.booleanSuppliers = booleanSuppliers;
	}
	
	@Override
	public boolean doesRequire(Subsystem subsystem) {
		return command.doesRequire(subsystem);
	}
	
	@Override
	protected void initialize() {
		for (BooleanSupplier booleanProvider : booleanSuppliers) {
			if (booleanProvider.getAsBoolean()) {
				return;
			}
		}
		command.start();
	}
	
	@Override
	protected boolean isFinished() {
		return !command.isRunning();
	}
}
