package org.usfirst.frc4904.standard.custom;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A SendableChooser for commands.
 * This allows us to put a command
 * choice on the smart dashboard.
 *
 */
public class CommandSendableChooser extends SendableChooser<CommandBase> {
	/**
	 * Adds the command object to the smart dashboard.
	 *
	 * @param object
	 */
<<<<<<< Updated upstream
	public void addObject(Command object) {
=======
	public void addOption(CommandBase object) {
>>>>>>> Stashed changes
		super.addOption(object.getName(), object);
	}

	/**
	 * Adds the command object to the smart dashboard
	 * as the default command.
	 *
	 * @param object
	 */
	public void addDefault(Command object) {
		super.setDefaultOption(object.getName() + " (default)", object);
	}
}
