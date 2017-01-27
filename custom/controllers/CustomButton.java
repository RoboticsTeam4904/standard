package org.usfirst.frc4904.standard.custom.controllers;


import org.usfirst.frc4904.standard.commands.Cancel;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A button with better toggle detection
 *
 */
public class CustomButton extends JoystickButton {
	protected boolean currentState;

	public CustomButton(GenericHID joystick, int buttonNumber) {
		super(joystick, buttonNumber);
		currentState = false;
	}

	/**
	 * Returns true the first time the button is pressed and the function is called.
	 *
	 * @return whether the button was pressed since the last call to this function
	 */
	public boolean getFirstPressed() {
		boolean buttonVal = super.get();
		if (currentState != buttonVal) {
			currentState = buttonVal;
			return buttonVal;
		}
		return false;
	}

	/**
	 * Cancels a command when the button is released
	 *
	 * @param command
	 *        The command to be cancelled.
	 */
	public void cancelWhenReleased(Command command) {
		whenReleased(new Cancel(command));
	}

	/**
	 * Run a command once when a button is held.
	 *
	 * @param command
	 *        The command to be run
	 */
	public void onlyWhileHeld(Command command) {
		whenPressed(command);
		cancelWhenReleased(command);
	}

	/**
	 * Runs a command unless a button is held.
	 *
	 * @param command
	 *        The command to be run.
	 */
	public void onlyWhileReleased(Command command) {
		command.start();
		whenReleased(command);
		cancelWhenPressed(command);
	}
}
