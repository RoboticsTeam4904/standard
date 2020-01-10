package org.usfirst.frc4904.standard.commands;

import java.util.Set;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RunWhile implements Command {
	protected final Command command;
	protected final Supplier<Boolean> stopCondition;

	public RunWhile(Command command, Supplier<Boolean> stopCondition) {
		this.command = command;
		this.stopCondition = stopCondition;
	}

	public void initialize() {
		command.schedule();
	}

	public boolean isFinished() {
		return !stopCondition.get();
	}

	public void end() {
		command.cancel();
	}

	public void interrupted() {
		end();
	}

	public Set<Subsystem> getRequirements() {
		return command.getRequirements();
	}
}
