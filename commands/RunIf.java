package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RunIf extends CommandGroup {
	protected final Command command;
	protected final BooleanInterface[] booleanInterfaces;
	
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
	public RunIf(Command command, BooleanInterface... booleanInterfaces) {
		super("RunIf[" + command.getName() + "]");
		this.command = command;
		this.booleanInterfaces = booleanInterfaces;
	}
	
	@Override
	protected void initialize() {
		for (BooleanInterface booleanInterface : booleanInterfaces) {
			if (!booleanInterface.evaluate()) {
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
