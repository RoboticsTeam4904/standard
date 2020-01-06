package org.usfirst.frc4904.standard.commands;


import java.util.StringJoiner;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.HashSet;
import java.util.Set;


public class Idle implements Command {
	public Idle() {
		super("Idle[No Subsystem]");
		setInterruptible(true);
	}

	public Idle(Subsystem... subsystems) {
		super("Idle[" + Idle.joinSubsystemNames(subsystems) + "]");
		setInterruptible(true);
		for (Subsystem subsystem : subsystems) {
			requires(subsystem);
		}
	}

	protected static String joinSubsystemNames(Subsystem[] subsystems) {
		StringJoiner subsystemNames = new StringJoiner("|");
		for (Subsystem subsystem : subsystems) {
			subsystemNames.add(subsystem.getName());
		}
		return subsystemNames.toString();
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {}

	@Override
	public Set<Subsystem> getRequirements() {
		return new HashSet<Subsystem>();
	}
}