package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is designed to allow
 * a driver or operator to kill a set
 * of subsystems. All it does is
 * trigger all of the commands in
 * parallel.
 */
public class Kill extends CommandGroup {
	public Kill(Command... kills) {
		super("Kill");
		setInterruptible(false); // should kill robot forever
		for (Command kill : kills) {
			addParallel(kill);
		}
	}
}
