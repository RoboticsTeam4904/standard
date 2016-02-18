package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class RunAll extends CommandGroup {
	public RunAll(String name) {
		super(name);
	}
	
	protected static String joinNames(Command... commands) {
		String[] names = new String[commands.length];
		for (int i = 0; i < commands.length; i++) {
			names[i] = commands[i].getName();
		}
		return String.join(", ", names);
	}
}
