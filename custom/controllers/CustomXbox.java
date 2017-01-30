package org.usfirst.frc4904.standard.custom.controllers;


/**
 * An XboxController that implements the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	public final static int LEFT_X_AXIS = 0;
	public final static int LEFT_Y_AXIS = 1;
	public final static int RIGHT_X_AXIS = 2;
	public final static int RIGHT_Y_AXIS = 3;
	public CustomButton a;
	public CustomButton b;
	public CustomButton x;
	public CustomButton y;
	public CustomButton lb;
	public CustomButton rb;
	public CustomButton back;
	public CustomButton start;

	public CustomXbox(final int port) {
		super(port);
		a = new CustomButton(controller, XboxController.A_BUTTON_ID);
		b = new CustomButton(controller, XboxController.B_BUTTON_ID);
		x = new CustomButton(controller, XboxController.X_BUTTON_ID);
		y = new CustomButton(controller, XboxController.Y_BUTTON_ID);
		lb = new CustomButton(controller, XboxController.LB_BUTTON_ID);
		rb = new CustomButton(controller, XboxController.RB_BUTTON_ID);
		back = new CustomButton(controller, XboxController.BACK_BUTTON_ID);
		start = new CustomButton(controller, XboxController.START_BUTTON_ID);
	}

	/**
	 * Axis 0: left joystick x Axis 1: left joystick y Axis 2: right joystick x
	 * Axis 2: right joystick y
	 */
	@Override
	public double getAxis(int axis) {
		switch (axis) {
			case LEFT_X_AXIS:
				return leftStick.getX();
			case LEFT_Y_AXIS:
				return leftStick.getY();
			case RIGHT_X_AXIS:
				return rightStick.getX();
			case RIGHT_Y_AXIS:
				return rightStick.getY();
			default:
				return super.getRawAxis(axis);
		}
	}
}
