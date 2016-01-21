package org.usfirst.frc4904.standard.custom.controllers;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * A button with better toggle detection
 *
 */
public class SuperButton extends JoystickButton {
	private boolean currentState;
	
	public SuperButton(GenericHID joystick, int buttonNumber) {
		super(joystick, buttonNumber);
		currentState = false;
	}
	
	/**
	 * Returns true the first time
	 * the button is pressed and
	 * the function is called.
	 */
	public boolean get() {
		boolean buttonVal = super.get();
		if (currentState != buttonVal) {
			currentState = buttonVal;
			return buttonVal;
		}
		return false;
	}
	
	/**
	 * Returns true whenever the button
	 * is depressed.
	 */
	public boolean getRaw() {
		return super.get();
	}
}
