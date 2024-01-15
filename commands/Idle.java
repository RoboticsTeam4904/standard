package org.usfirst.frc4904.standard.commands;

// import org.usfirst.frc4904.standard.LogKitten;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Requires subsystems so no other code can interact with them.
 */
public class Idle extends Command {
	boolean verbose;

	public Idle(String name, boolean verbose, Subsystem... subsystems) {
		super();
		setName(name);
		this.verbose = verbose;
		addRequirements(subsystems);
	}

	public Idle(boolean verbose, Subsystem... subsystems) {
		this("Idle", verbose, subsystems);
	}

	public Idle(Subsystem... subsystems) {
		this("Idle", false, subsystems);
	}

	@Override
	public void initialize() {
		if (verbose) {
			// LogKitten.v("Idle " + getName() + " initialized.");
		}
	}

	@Override
	public void execute() {
		if (verbose) {
			// LogKitten.v("Idle " + getName() + " executed.");
		}
	}

	@Override
	public boolean isFinished() {
		if (verbose) {
			// LogKitten.v("Idle " + getName() + " isFinished?");
		}
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		if (verbose) {
			if (interrupted) {
				// LogKitten.v("Idle " + getName() + "ended with interrupt.");
			} else {
				// LogKitten.v("Idle " + getName() + " ended.");
			}
		}
	}
}
