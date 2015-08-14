package org.usfirst.frc4904.cmdbased.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Kill extends CommandGroup {
	public Kill(Command... kills) {
		super("Kill");
		setInterruptible(false); // should kill robot forever
		for (Command kill : kills) {
			addParallel(kill);
		}
	}
}
