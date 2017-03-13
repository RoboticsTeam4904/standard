package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.custom.Overridable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class OverridableCommandGroup extends CommandGroup {
	protected final Overridable overridable;

	public OverridableCommandGroup() {
		super();
		overridable = null;
	}

	public OverridableCommandGroup(String name) {
		super(name);
		overridable = null;
	}

	public OverridableCommandGroup(Overridable overridable) {
		super();
		this.overridable = overridable;
	}

	public OverridableCommandGroup(String name, Overridable overridable) {
		super(name);
		this.overridable = overridable;
	}

	public final synchronized void addSequentialUnlessOverridden(Command command) {
		checkOverridable();
		addSequentialUnlessOverridden(command, overridable);
	}

	public final synchronized void addSequentialUnlessOverridden(Command command, double timeout) {
		checkOverridable();
		addSequentialUnlessOverridden(command, timeout, overridable);
	}

	public final synchronized void addParallelUnlessOverridden(Command command) {
		checkOverridable();
		addParallelUnlessOverridden(command, overridable);
	}

	public final synchronized void addParallelUnlessOverridden(Command command, double timeout) {
		checkOverridable();
		addParallelUnlessOverridden(command, timeout, overridable);
	}

	public final synchronized void addSequentialIfOverridden(Command command) {
		checkOverridable();
		addSequentialIfOverridden(command, overridable);
	}

	public final synchronized void addSequentialIfOverridden(Command command, double timeout) {
		checkOverridable();
		addSequentialIfOverridden(command, timeout, overridable);
	}

	public final synchronized void addParallelIfOverridden(Command command) {
		checkOverridable();
		addParallelIfOverridden(command, overridable);
	}

	public final synchronized void addParallelIfOverridden(Command command, double timeout) {
		checkOverridable();
		addParallelIfOverridden(command, timeout, overridable);
	}

	public final synchronized void addSequentialUnlessOverridden(Command command, Overridable overridable) {
		addSequential(new RunIf(command, overridable::isNotOverridden));
	}

	public final synchronized void addSequentialUnlessOverridden(Command command, double timeout, Overridable overridable) {
		addSequential(new RunIf(command, overridable::isNotOverridden), timeout);
	}

	public final synchronized void addParallelUnlessOverridden(Command command, Overridable overridable) {
		addParallel(new RunIf(command, overridable::isNotOverridden));
	}

	public final synchronized void addParallelUnlessOverridden(Command command, double timeout, Overridable overridable) {
		addParallel(new RunIf(command, overridable::isNotOverridden), timeout);
	}

	public final synchronized void addSequentialIfOverridden(Command command, Overridable overridable) {
		addSequential(new RunIf(command, overridable::isOverridden));
	}

	public final synchronized void addSequentialIfOverridden(Command command, double timeout, Overridable overridable) {
		addSequential(new RunIf(command, overridable::isOverridden), timeout);
	}

	public final synchronized void addParallelIfOverridden(Command command, Overridable overridable) {
		addParallel(new RunIf(command, overridable::isOverridden));
	}

	public final synchronized void addParallelIfOverridden(Command command, double timeout, Overridable overridable) {
		addParallel(new RunIf(command, overridable::isOverridden), timeout);
	}

	private synchronized void checkOverridable() {
		if (overridable == null) {
			throw new OverridableCommandGroupNullException();
		}
	}

	private class OverridableCommandGroupNullException extends RuntimeException {
		private static final long serialVersionUID = -4840820399306678968L;

		public OverridableCommandGroupNullException() {
			super("OverridableCommandGroup was not constructed with an Overridable, so there is no default Overridable to use");
		}
	}
}
