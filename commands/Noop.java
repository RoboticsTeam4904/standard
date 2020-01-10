package org.usfirst.frc4904.standard.commands;

import java.util.Set;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class Noop implements Command {
	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void end(boolean wasInterrupted) {
	}

	@Override
	public Set<Subsystem> getRequirements() {
		return Set.of();
	}
}
