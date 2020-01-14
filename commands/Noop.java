package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Noop extends CommandBase {
	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void end(boolean wasInterrupted) {
	}
}
