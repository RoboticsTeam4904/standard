package org.usfirst.frc4904.standard.commands;


import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Threshold command takes in a command, a supplier, and a threshold.
 * When the threshold is passed by the supplier, it starts the command.
 * When the threshold stops being passed, it cancels the command.
 */
public class ThresholdCommand<T extends Comparable<T>> extends Command {
	protected final Command command;
	protected final Supplier<T> supplier;
	protected final T threshold;
	protected final boolean invert;

	public ThresholdCommand(Command command, Supplier<T> supplier, T threshold, boolean invert) {
		this.command = command;
		this.supplier = supplier;
		this.threshold = threshold;
		this.invert = invert;
	}

	public ThresholdCommand(Command command, Supplier<T> axis, T threshold) {
		this(command, axis, threshold, false);
	}

	protected boolean pastThreshold() {
		return ((supplier.get().compareTo(threshold) >= 0) != invert);
	}

	@Override
	protected void execute() {
		if (pastThreshold() && !command.isRunning()) {
			command.start();
		} else if (!pastThreshold() && command.isRunning()) {
			command.cancel();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
