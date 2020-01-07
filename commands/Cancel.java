package org.usfirst.frc4904.standard.commands;

import java.util.Set;

import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Cancel implements Command {
	protected final Command command;

	public Cancel(Command command) {
		this.command = command;
	}

	public void initialize() {
		LogKitten.v("Initializing " + getName());
		command.cancel();
	}

	public void execute() {}

	public boolean isFinished() {
		return true;
	}

	public void end() {}

	public Set<Subsystem> getRequirements()
	{
		return Set.of();
	}

	public void interrupted() {}
}
