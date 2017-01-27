package org.usfirst.frc4904.standard.commands;


import java.util.StringJoiner;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class RunAll extends CommandGroup {
	public RunAll(String name) {
		super(name);
	}

	protected static String joinNames(Command... commands) {
		StringJoiner joiner = new StringJoiner(", ");
		for (Command c : commands) {
			joiner.add(c.getName());
		}
		return joiner.toString();
	}
}
