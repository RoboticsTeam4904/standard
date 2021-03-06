package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.custom.Overridable;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public abstract class OverridableParallelCommandGroup extends ParallelCommandGroup {
	protected final Overridable overridable;

	public OverridableParallelCommandGroup(Overridable overridable) {
		super();
		this.overridable = overridable;
	}

	public OverridableParallelCommandGroup(String name, Overridable overridable) {
		this(overridable);
		setName(name);
	}

	public final synchronized void addParallelUnlessOverridden(CommandBase command) {
		addCommands(new RunIf(command, overridable::isNotOverridden));
	}

	public final synchronized void addParallelUnlessOverridden(CommandBase command, double timeout) {
		addCommands(new RunIf(command.withTimeout(timeout), overridable::isNotOverridden));
	}

	public final synchronized void addParallelIfOverridden(CommandBase command) {
		addCommands(new RunIf(command, overridable::isOverridden));
	}

	public final synchronized void addParallelIfOverridden(CommandBase command, double timeout) {
		addCommands(new RunIf(command.withTimeout(timeout), overridable::isOverridden));
	}
}
