package org.usfirst.frc4904.standard.custom.controllers;


/**
 * An XboxController that implements
 * the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	public final CustomButton customA;
	public final CustomButton customB;
	public final CustomButton customX;
	public final CustomButton customY;
	public final CustomButton customLb;
	public final CustomButton customRb;
	public final CustomButton customBack;
	public final CustomButton customStart;
	
	public CustomXbox(final int port) {
		super(port);
		this.customA = new CustomButton(this.controller, A_BUTTON_ID);
		this.customB = new CustomButton(this.controller, B_BUTTON_ID);
		this.customX = new CustomButton(this.controller, X_BUTTON_ID);
		this.customY = new CustomButton(this.controller, Y_BUTTON_ID);
		this.customLb = new CustomButton(this.controller, LB_BUTTON_ID);
		this.customRb = new CustomButton(this.controller, RB_BUTTON_ID);
		this.customBack = new CustomButton(this.controller, BACK_BUTTON_ID);
		this.customStart = new CustomButton(this.controller, START_BUTTON_ID);
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
