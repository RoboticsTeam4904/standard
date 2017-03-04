package org.usfirst.frc4904.standard.commands;


import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ThresholdCommand extends Command {
	protected final Command command;
	protected final Supplier<Double> axis;
	protected final double threshold;
	protected final boolean invert;

	public ThresholdCommand(Command command, Supplier<Double> axis, double threshold, boolean invert) {
		this.command = command;
		this.axis = axis;
		this.threshold = threshold;
		this.invert = invert;
	}

	public ThresholdCommand(Command command, Supplier<Double> axis, double threshold) {
		this(command, axis, threshold, false);
	}

	protected boolean pastThreshold(double value) {
		return (!invert && value >= threshold) || (invert && value <= threshold);
	}

	@Override
	protected void execute() {
		if (pastThreshold(axis.get()) && !command.isRunning()) {
			command.start();
		} else if (!pastThreshold(axis.get()) && command.isRunning()) {
			command.cancel();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
