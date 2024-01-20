package org.usfirst.frc4904.standard.custom;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * A SendableChooser for commands. This allows us to put a command choice on the
 * smart dashboard.
 *
 */
// public class CommandSendableChooser extends SendableChooser<Command> {	// OPTIM: A LOT of stuff in standard takes in Command when it should probably take in Command (unintuitively, Command is actually less basic than Command)
public class CommandSendableChooser extends SendableChooser<Command> {
	/**
	 * Adds the command object to the smart dashboard.
	 *
	 * @param object
	 */
	public void addOption(Command object) {
		super.addOption(object.getName(), object);
	}

	/**
	 * Adds the command object to the smart dashboard as the default command.
	 *
	 * @param object
	 */
	public void setDefaultOption(Command object) {
		super.setDefaultOption(object.getName() + " (default)", object);
	}
}
