package org.usfirst.frc4904.standard.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RunWhile extends CommandBase {
	protected final CommandBase command;
	protected final Supplier<Boolean> stopCondition;

	public RunWhile(String name, CommandBase command, Supplier<Boolean> stopCondition) {
		super();
		setName(name);
		this.command = command;
		this.stopCondition = stopCondition;
	}

	public RunWhile(CommandBase command, Supplier<Boolean> stopCondition) {
		this("RunWhile", command, stopCondition);
	}

	@Override
	public void initialize() {
		command.schedule();
	}

	@Override
	public boolean isFinished() {
		return !stopCondition.get();
	}

	@Override
	public void end(boolean interrupted) {
		command.cancel();
	}
}
