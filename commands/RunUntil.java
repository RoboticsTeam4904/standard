package org.usfirst.frc4904.standard.commands;


import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;

public class RunUntil extends Command {
	protected final Command command;
	protected final Supplier<Boolean> stopCondition;

	public RunUntil(Command command, Supplier<Boolean> stopCondition) {
		this.command = command;
		this.stopCondition = stopCondition;
	}

	@Override
	protected void initialize() {
		command.start();
	}

	@Override
	protected boolean isFinished() {
		return stopCondition.get();
	}

	@Override
	protected void end() {
		command.cancel();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
