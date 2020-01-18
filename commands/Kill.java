package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * This command is designed to allow a driver or operator to kill a set of
 * subsystems. All it does is trigger all of the commands in parallel.
 */
public class Kill extends ParallelCommandGroup {
	public Kill(CommandBase... kills) {
		super();
		setName("Kill");
		for (CommandBase kill : kills) {
			addCommands(kill);
		}
	}
}
