package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj2.command.Command;;

public class Noop extends Command {
	public Noop() {
		super("Noop");
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void end(boolean interrupted) {}

	// @Override
	// protected void interrupted() {}
}
