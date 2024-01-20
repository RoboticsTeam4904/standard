package org.usfirst.frc4904.standard.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Command;

public class RunWhile extends Command {
	protected final Command command;
	protected final Supplier<Boolean> stopCondition;

	public RunWhile(String name, Command command, Supplier<Boolean> stopCondition) {
		super();
		setName(name);
		this.command = command;
		this.stopCondition = stopCondition;
	}

	public RunWhile(Command command, Supplier<Boolean> stopCondition) {
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
