package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

<<<<<<< HEAD
public class Noop extends CommandBase {
	public Noop() {	
		super();	
		setName("NOOP");
=======

public class Noop extends CustomCommand {
	public Noop() {
		super("Noop");
>>>>>>> 2020-update
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
