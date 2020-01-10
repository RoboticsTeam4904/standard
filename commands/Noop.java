package org.usfirst.frc4904.standard.commands;

import java.util.Set;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Noop extends CommandBase {
	public void initialize() {
	}

	public void execute() {
	}

	public boolean isFinished() {
		return true;
	}

	public void end(boolean wasInterrupted) {
	}

	public Set<Subsystem> getRequirements() {
		return Set.of();
	}
}
