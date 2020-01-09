package org.usfirst.frc4904.standard.commands;

import java.util.Set;

import org.usfirst.frc4904.standard.LogKitten;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Requires subsystems so no other code can interact with them.
 */
public class Idle extends CustomCommand { 
	boolean verbose;
	Set<Subsystem> requirements;

	public Idle(String name, boolean verbose, Subsystem... subsystems)
	{
		super(name);
		this.verbose = verbose;
		requirements = Set.of(subsystems);
	}
	public Idle(boolean verbose, Subsystem... subsystems) {
		this("Idle", verbose, subsystems);
	}

	public Idle(Subsystem... subsystems) {
		this("Idle", false, subsystems);
	}

	public void initialize() {
		if (verbose) {
			LogKitten.v("Idle " + getName() + " initialized.");
		}
	}

	public void execute() {
		if (verbose) {
			LogKitten.v("Idle " + getName() + " executed.");
		}
	}

	public boolean isFinished() {
		if (verbose) {
			LogKitten.v("Idle " + getName() + " isFinished?");
		}
		return false;
	}

	protected void end() {
		if (verbose) {
			LogKitten.v("Idle " + getName() + " end.");
		}
	}

	protected void interrupted() {
		if (verbose) {
			LogKitten.v("Idle " + getName() + " interrupted.");
		}
	}
}
