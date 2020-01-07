package org.usfirst.frc4904.standard.commands;

import java.util.Set;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class Noop implements Command {
	public void initialize() {}

	public void execute() {}

	public boolean isFinished() {
		return true;
	}

	public void end() {}

	public void interrupted() {}

	public Set<Subsystem> getRequirements() {
		return Set.of();
	}
}
