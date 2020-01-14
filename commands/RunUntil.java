package org.usfirst.frc4904.standard.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunUntil extends CommandBase {
	protected final CommandBase command;
	protected final Supplier<Boolean> stopCondition;

	public RunUntil(CommandBase command, Supplier<Boolean> stopCondition) {
		this.command = command;
		this.stopCondition = stopCondition;
		addRequirements((Subsystem[]) command.getRequirements().toArray());
	}

	public void initialize() {
		command.schedule();
	}

	public boolean isFinished() {
		return stopCondition.get();
	}

	public void end() {
		command.cancel();
	}

	public void interrupted() {
		end();
	}
}
