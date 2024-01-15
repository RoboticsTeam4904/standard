package org.usfirst.frc4904.standard.commands;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.Command;

public class RunIfElse extends Command {
	protected final Command ifCommand;
	protected final Command elseCommand;
	protected Command runningCommand;
	protected final BooleanSupplier[] booleanSuppliers;
	protected boolean hasRunOnce;

	public RunIfElse(String name, Command ifCommand, Command elseCommand,
			BooleanSupplier... booleanSuppliers) {
		super();
		setName(name);
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.booleanSuppliers = booleanSuppliers;
	}

	public RunIfElse(Command ifCommand, Command elseCommand, BooleanSupplier... booleanSuppliers) {
		this("RunIfElse", ifCommand, elseCommand, booleanSuppliers);
	}

	@Override
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

	@Override
	public boolean isFinished() {
		if (runningCommand.isScheduled() && !hasRunOnce) {
			hasRunOnce = true;
		}
		return !runningCommand.isScheduled() && hasRunOnce;
	}

	@Override
	public void end(boolean interrupted) {
		ifCommand.cancel();
		elseCommand.cancel();
		hasRunOnce = false;
	}
}
