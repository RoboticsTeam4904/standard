package org.usfirst.frc4904.standard.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RunIfElse implements Command {
	protected final Command ifCommand;
	protected final Command elseCommand;
	protected Command runningCommand;
	protected final BooleanSupplier[] booleanSuppliers;
	protected boolean hasRunOnce;

	protected final Set<Subsystem> unionRequirements;

	protected RunIfElse(Command ifCommand, Command elseCommand, BooleanSupplier... booleanSuppliers) {
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.booleanSuppliers = booleanSuppliers;

		unionRequirements = new HashSet<Subsystem>(ifCommand.getRequirements());
		unionRequirements.addAll(elseCommand.getRequirements());
	}

	public void initialize() {
		for (BooleanSupplier booleanSupplier : booleanSuppliers) {
			if (!booleanSupplier.getAsBoolean()) {
				elseCommand.schedule();
				runningCommand = elseCommand;
				return;
			}
		}
		ifCommand.schedule();
		runningCommand = ifCommand;
	}

	public void execute() {}

	public boolean isFinished() {
		if (runningCommand.isScheduled() && !hasRunOnce) {
			hasRunOnce = true;
		}
		return !runningCommand.isScheduled() && hasRunOnce;
	}

	public void end() {
		ifCommand.cancel();
		elseCommand.cancel();
		hasRunOnce = false;
	}

	public void interrupted() {
		end();
	}

	public Set<Subsystem> getRequirements() {
		return unionRequirements;
	}
}
