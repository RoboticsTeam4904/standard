package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class Noop extends Command {
	public Noop() {
		super();
		setName("NOOP");
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
