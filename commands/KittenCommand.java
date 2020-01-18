package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class KittenCommand extends Command {
	protected final String message;
	protected final LogKitten.KittenLevel level;

	public KittenCommand(String message, LogKitten.KittenLevel level) {
		this.message = message;
		this.level = level;
	}

	@Override
	protected void initialize() {
		LogKitten.logMessage(message, level, false);
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
