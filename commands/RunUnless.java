package org.usfirst.frc4904.standard.commands;


import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.command.Command;

public class RunUnless extends RunIfElse {
	/**
	 * Run a command based on a conditional callback.
	 * For example, if you only want to shoot if a shooter is safe (based on its isUnsafe() function), use:
	 * new RunIf(new Shoot(), shooter::isUnsafe)
	 * This double-colon syntax only works in Java 8. If you must use this with an earlier version of Java, use:
	 * new RunIf(new Shoot(), new BooleanInterface() { boolean evaluate() { return shooter.isUnsafe(); } })
	 *
	 * @param command
	 *        The command to be run if the condition is NOT met
	 * @param booleanSuppliers
	 *        A condition function using Java 8's colon syntax (will run unless the condition is true)
	 */
	public RunUnless(Command command, BooleanSupplier... booleanSuppliers) {
		super("RunUnless[" + command.getName() + "]", new Noop(), command, booleanSuppliers);
	}
}
