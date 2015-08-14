package org.usfirst.frc4904.cmdbased.custom;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class SuperButton extends JoystickButton {
	private boolean currentState;
	
	public SuperButton(GenericHID joystick, int buttonNumber) {
		super(joystick, buttonNumber);
		// TODO Auto-generated constructor stub
		currentState = false;
	}
	
	public boolean get() {
		boolean buttonVal = super.get();
		if (currentState != buttonVal) {
			currentState = buttonVal;
			return buttonVal;
		}
		return false;
	}

	public boolean getRaw() {
		return super.get();
	}
}
