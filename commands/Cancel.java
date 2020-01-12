package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This class cancels a command
 */
public class Cancel extends CommandBase {
	protected final CommandBase command;

	public Cancel(CommandBase command) {
		super();
		setName("Cancel " + command.getName());
		this.command = command;
	}

	public void initialize() {
		LogKitten.v("Initializing " + getName());
		command.cancel();
	}

	public boolean isFinished() {
		return true;
	}
}
