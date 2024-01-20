package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class SpawnCommand extends Command {
	private final Command childCommand;

	public SpawnCommand(Command childCommand) {
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
