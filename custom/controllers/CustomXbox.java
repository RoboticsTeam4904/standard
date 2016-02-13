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
	 */
	public double getAxis(int axis) {
		switch (axis) {
			case 0:
				return this.leftStick.getX();
			case 1:
				return this.leftStick.getY();
			case 2:
				return this.rightStick.getX();
			default:
				return super.getRawAxis(axis);
		}
	}
}
