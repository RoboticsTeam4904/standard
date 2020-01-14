package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpawnCommand extends CommandBase {
	private final CommandBase childCommand;

	public SpawnCommand(CommandBase childCommand) {
		super();
		setName("SpawnCommand[" + childCommand.getName() + "]");
		this.childCommand = childCommand;
	}

	@Override
	public void initialize() {
		childCommand.schedule();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
