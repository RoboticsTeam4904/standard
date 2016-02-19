package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public class RunAllParallel extends RunAll {
	/**
	 * Run a variable number of commands in parallel.
	 * This is a convenience class for simple use of CommandGroup.
	 * For example, if you want to raise an elevator an blare an airhorn at the same time, use:
	 * new RunAllParallel(new SetElevatorHeight(Elevator.MAX_HEIGHT), new BlareAirhorn())
	 * If you will be using the same RunAllParallel more than once, make a new class and extend CommandGroup.
	 *
	 * @param commands
	 *        The commands to be run in parallel
	 */
	public RunAllParallel(Command... commands) {
		super("RunAllParallel[" + RunAll.joinNames(commands) + "]");
		for (Command command : commands) {
			addParallel(command);
		}
	}
}
