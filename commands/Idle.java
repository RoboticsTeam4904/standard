package org.usfirst.frc4904.standard.commands;


import java.util.StringJoiner;
import java.util.Set;
import java.util.Collections;

import org.usfirst.frc4904.standard.LogKitten;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Idle implements Command {
	boolean verbose;
	public Idle(boolean verbose, Subsystem... subsystems) {
		this.verbose = verbose;
		Set<Subsystem> requirements = Collections.emptySet(); // as per https://stackoverflow.com/a/285184
		for (Subsystem subsystem : subsystems) {
			requirements.add(subsystem);
		}
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
