package org.usfirst.frc4904.standard.commands;



public class Noop extends CustomCommand {
	public Noop() {
		super("Noop");
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
