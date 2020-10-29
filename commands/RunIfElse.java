package org.usfirst.frc4904.standard.commands;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RunIfElse extends CommandBase {
	protected final CommandBase ifCommand;
	protected final CommandBase elseCommand;
	protected CommandBase runningCommand;
	protected final BooleanSupplier[] booleanSuppliers;
	protected boolean hasRunOnce;

	public RunIfElse(String name, CommandBase ifCommand, CommandBase elseCommand,
			BooleanSupplier... booleanSuppliers) {
		super();
		setName(name);
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
		this.booleanSuppliers = booleanSuppliers;
	}

	public RunIfElse(CommandBase ifCommand, CommandBase elseCommand, BooleanSupplier... booleanSuppliers) {
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
