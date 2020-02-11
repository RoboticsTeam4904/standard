package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.LogKitten;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This class cancels a command
 */
public class CancelCurrent extends CommandBase {
	protected final SubsystemBase subsystem;

	public CancelCurrent(SubsystemBase subsystem) {
		super();
		setName("Cancel " + subsystem.getName() + "'s current command.");
		this.subsystem = subsystem;
	}

	public void initialize() {
		LogKitten.v("Initializing " + getName());
		if(subsystem.getCurrentCommand() != null){
            subsystem.getCurrentCommand().cancel();
        }
	}

	public boolean isFinished() {
		return true;
	}
}
