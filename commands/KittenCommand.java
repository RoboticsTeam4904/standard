package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class KittenCommand implements Command {
	protected final String message;
	protected final LogKitten.KittenLevel level;

	public KittenCommand(String message, LogKitten.KittenLevel level) {
		this.message = message;
		this.level = level;
	}

	@Override
	public void initialize() {
		LogKitten.logMessage(message, level, false);
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void getRequirements() {
		return
	}

	/*
	 * @Override protected void end() {}
	 * 
	 * @Override protected void interrupted() {}
	 */
}
