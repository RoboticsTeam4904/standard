package org.usfirst.frc4904.standard.custom.controllers;


import edu.wpi.first.wpilibj.XboxController;

/**
 * An XboxController that implements the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	public final static int LEFT_X_AXIS = 0;
	public final static int LEFT_Y_AXIS = 1;
	public final static int RIGHT_X_AXIS = 2;
	public final static int RIGHT_Y_AXIS = 3;
	protected static final int A_BUTTON_ID = 1;
	protected static final int B_BUTTON_ID = 2;
	protected static final int X_BUTTON_ID = 3;
	protected static final int Y_BUTTON_ID = 4;
	protected static final int LB_BUTTON_ID = 5;
	protected static final int RB_BUTTON_ID = 6;
	protected static final int BACK_BUTTON_ID = 7;
	protected static final int START_BUTTON_ID = 8;
	protected static final int LEFT_THUMBSTICK_BUTTON_ID = 9;
	protected static final int RIGHT_THUMBSTICK_BUTTON_ID = 10;
	public CustomButton a;
	public CustomButton b;
	public CustomButton x;
	public CustomButton y;
	public CustomButton lb;
	public CustomButton rb;
	public CustomButton back;
	public CustomButton start;
	public CustomButton lstickbutton;
	public CustomButton rstickbutton;

	public CustomXbox(final int port) {
		super(port);
		a = new CustomButton(this, CustomXbox.A_BUTTON_ID);
		b = new CustomButton(this, CustomXbox.B_BUTTON_ID);
		x = new CustomButton(this, CustomXbox.X_BUTTON_ID);
		y = new CustomButton(this, CustomXbox.Y_BUTTON_ID);
		lb = new CustomButton(this, CustomXbox.LB_BUTTON_ID);
		rb = new CustomButton(this, CustomXbox.RB_BUTTON_ID);
		back = new CustomButton(this, CustomXbox.BACK_BUTTON_ID);
		start = new CustomButton(this, CustomXbox.START_BUTTON_ID);
		lstickbutton = new CustomButton(this, CustomXbox.LEFT_THUMBSTICK_BUTTON_ID);
		rstickbutton = new CustomButton(this, CustomXbox.RIGHT_THUMBSTICK_BUTTON_ID);
	}

	/**
	 * Axis 0: left joystick x Axis 1: left joystick y Axis 2: right joystick x
	 * Axis 2: right joystick y
	 */

	public static double computeDeadzone(double input, double deadzone) {
		if (deadzone < 0 || deadzone >= 1) {
			deadzone = 0; // Prevent any weird errors
		}
		double result = Math.signum(input) * ((Math.abs(input) / (1 - deadzone)) + deadzone);
		return result;
	}

	@Override
	public double getAxis(int axis) {
		switch (axis) {
		case LEFT_X_AXIS:
			return this.getX(Hand.kLeft);
		case LEFT_Y_AXIS:
			return this.getY(Hand.kLeft);
		case RIGHT_X_AXIS:
			return this.getX(Hand.kRight);
		case RIGHT_Y_AXIS:
			return this.getY(Hand.kRight);
		default:
			return super.getRawAxis(axis);
		}
	}
}
