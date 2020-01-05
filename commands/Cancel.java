package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Command;

public class Cancel extends CommandBase implements Command
{
	protected final Command command;

	public Cancel(Command command) 
	{
		this.command = command;
	}

	@Override
	public void initialize() 
	{
		LogKitten.v("Initializing " + getName());
		command.cancel();
	}

	@Override
	public void execute() {}

	@Override
	public boolean isFinished() 
	{
		return true;
	}

	@Override
	protected void end() 
	{
	}

	@Override
	protected void interrupted() 
	{
	}
}
