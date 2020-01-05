package org.usfirst.frc4904.standard.commands;


import java.util.HashSet;
import java.util.Set;

import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Cancel implements Command {
	protected final Command command;

	public Cancel(Command command) {
		//setInterruptible(false);
		this.command = command;
	}

	@Override
	public void initialize() 
	{
		LogKitten.v("Initializing " + getName());
		command.cancel();
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public Set<Subsystem> getRequirements() {
		return new HashSet<Subsystem>();
	}
}
