package org.usfirst.frc4904.standard.custom.controllers;


/**
 * An XboxController that implements
 * the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	public CustomXbox(final int port) {
		super(port);
	}
	
	/**
	 * Axis 0: left joystick x
	 * Axis 1: left joystick y
	 * Axis 2: right joystick x
	 * Axis 2: right joystick y
	 */
	public double getAxis(int axis) {
		switch (axis) {
			case 0:
				return leftStick.getX();
			case 1:
				return leftStick.getY();
			case 2:
				return rightStick.getX();
			case 3:
				return rightStick.getY();
			default:
				return super.getRawAxis(axis);
		}
	}
}
