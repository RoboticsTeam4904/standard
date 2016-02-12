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
	private boolean currentState;
	
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
	
	public void cancelWhenReleased(Command command) {
		whenReleased(new Cancel(command));
	}
	
	public void onlyWhileHeld(Command command) {
		whenPressed(command);
		cancelWhenReleased(command);
	}
	
	public void onlyWhileReleased(Command command) {
		whenReleased(command);
		cancelWhenPressed(command);
	}
}
