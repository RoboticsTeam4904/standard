package org.usfirst.frc4904.standard.custom;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * A SendableChooser for commands.
 * This allows us to put a command
 * choice on the smart dashboard.
 *
 */
public class CommandSendableChooser extends SendableChooser {
	/**
	 * Adds the command object to the smart dashboard.
	 *
	 * @param object
	 */
	public void addObject(Command object) {
		super.addObject(object.getName(), object);
	}
	
	/**
	 * Adds the command object to the smart dashboard
	 * as the default command.
	 *
	 * @param object
	 */
	public void addDefault(Command object) {
		super.addDefault(object.getName() + " (default)", object);
	}
	
	/**
	 * Returns the command selected on the smart
	 * dashboard.
	 */
	@Override
	public Command getSelected() {
		return (Command) super.getSelected();
	}
}
