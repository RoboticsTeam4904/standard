package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.custom.Overridable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class OverridableCommandGroup extends CommandGroup {
	protected final Overridable overridable;

	public OverridableCommandGroup(Overridable overridable) {
		super();
		this.overridable = overridable;
	}

	public OverridableCommandGroup(String name, Overridable overridable) {
		super(name);
		this.overridable = overridable;
	}

	public final synchronized void addSequentialUnlessOverridden(Command command) {
		addSequential(new RunIf(command, overridable::isNotOverridden));
	}

	public final synchronized void addSequentialUnlessOverridden(Command command, double timeout) {
		addSequential(new RunIf(command, overridable::isNotOverridden), timeout);
	}

	public final synchronized void addParallelUnlessOverridden(Command command) {
		addParallel(new RunIf(command, overridable::isNotOverridden));
	}

	public final synchronized void addParallelUnlessOverridden(Command command, double timeout) {
		addParallel(new RunIf(command, overridable::isNotOverridden), timeout);
	}

	public final synchronized void addSequentialIfOverridden(Command command) {
		addSequential(new RunIf(command, overridable::isOverridden));
	}

	public final synchronized void addSequentialIfOverridden(Command command, double timeout) {
		addSequential(new RunIf(command, overridable::isOverridden), timeout);
	}

	public final synchronized void addParallelIfOverridden(Command command) {
		addParallel(new RunIf(command, overridable::isOverridden));
	}

	public final synchronized void addParallelIfOverridden(Command command, double timeout) {
		addParallel(new RunIf(command, overridable::isOverridden), timeout);
	}
}
