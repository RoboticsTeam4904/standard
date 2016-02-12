package org.usfirst.frc4904.standard.custom.controllers;


/**
 * An XboxController that implements
 * the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	public CustomXbox(final int port) {
		super(port);
		this.a = new CustomButton(this.controller, A_BUTTON_ID);
		this.b = new CustomButton(this.controller, B_BUTTON_ID);
		this.x = new CustomButton(this.controller, X_BUTTON_ID);
		this.y = new CustomButton(this.controller, Y_BUTTON_ID);
		this.lb = new CustomButton(this.controller, LB_BUTTON_ID);
		this.rb = new CustomButton(this.controller, RB_BUTTON_ID);
		this.back = new CustomButton(this.controller, BACK_BUTTON_ID);
		this.start = new CustomButton(this.controller, START_BUTTON_ID);
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
