package org.usfirst.frc4904.standard.commands;

import java.util.Set;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class RunUntil implements Command {
	protected final Command command;
	protected final Supplier<Boolean> stopCondition;

	public RunUntil(Command command, Supplier<Boolean> stopCondition) {
		this.command = command;
		this.stopCondition = stopCondition;
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

	public Set<Subsystem> getRequirements() {
		return this.command.getRequirements();
	}
}
