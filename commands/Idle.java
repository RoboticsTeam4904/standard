package org.usfirst.frc4904.standard.commands;

import java.util.Set;

import org.usfirst.frc4904.standard.LogKitten;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Requires subsystems so no other code can interact with them.
 */
public class Idle implements Command {
	boolean verbose;
	Set<Subsystem> requirements;
	public Idle(boolean verbose, Subsystem... subsystems) {
		this.verbose = verbose;
		requirements = Subsystem.of(subsystems);
	}

	public Idle(Subsystem... subsystems) {
		this(false, subsystems);
	}

	public void initialize() {
		if (verbose) {
			LogKitten.v("Idle(verbose) initialized.");
		}
	}

	public void execute() {
		if (verbose) {
			LogKitten.v("Idle(verbose) executed.");
		}
	}

	public boolean isFinished() {
		if (verbose) {
			LogKitten.v("Idle(verbose) isFinished?");
		}
		return false;
	}

	protected void end() {
		if (verbose) {
			LogKitten.v("Idle(verbose) end.");
		}
	}

	protected void interrupted() {
		if (verbose) {
			LogKitten.v("Idle(verbose) interrupted.");
		}
	}
}
