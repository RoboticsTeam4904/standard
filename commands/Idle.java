package org.usfirst.frc4904.standard.commands;

import java.util.StringJoiner;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Idle extends Command {
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
	protected void initialize() {
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
