package org.usfirst.frc4904.standard.custom.controllers;


import edu.wpi.first.wpilibj.XboxController;

/**
 * An XboxController that implements
 * the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	public final static int LEFT_X_AXIS = 0;
	public final static int LEFT_Y_AXIS = 1;
	public final static int RIGHT_X_AXIS = 2;
	public final static int RIGHT_Y_AXIS = 3;
	public final CustomButton a;
	public final CustomButton b;
	public final CustomButton x;
	public final CustomButton y;
	public final CustomButton lb;
	public final CustomButton rb;
	public final CustomButton back;
	public final CustomButton start;
	public final CustomButton leftStick;
	public final CustomButton rightStick;

	public CustomXbox(final int port) {
		super(port);
		a = new CustomButton(this, 1);
		b = new CustomButton(this, 2);
		x = new CustomButton(this, 3);
		y = new CustomButton(this, 4);
		lb = new CustomButton(this, 5);
		rb = new CustomButton(this, 6);
		back = new CustomButton(this, 7);
		start = new CustomButton(this, 8);
		leftStick = new CustomButton(this, 9);
		rightStick = new CustomButton(this, 10);
	}

	public double getAxis(int axis) {
		switch (axis) {
			case LEFT_X_AXIS:
				return getX(Hand.kLeft);
			case LEFT_Y_AXIS:
				return getY(Hand.kLeft);
			case RIGHT_X_AXIS:
				return getX(Hand.kRight);
			case RIGHT_Y_AXIS:
				return getY(Hand.kRight);
			default:
				return getRawAxis(axis);
		}
	}
}
