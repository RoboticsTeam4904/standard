package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * This command is designed to allow a driver or operator to kill a set of
 * subsystems. All it does is trigger all of the commands in parallel.
 */
public class Kill extends ParallelCommandGroup {
	public Kill(Command... kills) {
		super();
		setName("Kill");
		for (Command kill : kills) {
			addCommands(kill);
		}
	}
}
