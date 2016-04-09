package org.usfirst.frc4904.standard.custom.controllers;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

/**
 * A joystick that implements the generic controller interface.
 * This allows us to use a joystick as a controller.
 * This contains 12 buttons to reflect the joysticks we are typically using.
 */
public class CustomJoystick extends Joystick implements Controller {
	public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	public static final int SLIDER_AXIS = 3;
	protected double deadzone;
	protected final int port;
	// Buttons
	public final CustomButton button1;
	public final CustomButton button2;
	public final CustomButton button3;
	public final CustomButton button4;
	public final CustomButton button5;
	public final CustomButton button6;
	public final CustomButton button7;
	public final CustomButton button8;
	public final CustomButton button9;
	public final CustomButton button10;
	public final CustomButton button11;
	public final CustomButton button12;
	
	public CustomJoystick(int port) {
		super(port);
		this.port = port;
		deadzone = 0;
		button1 = new CustomButton(this, 1);
		button2 = new CustomButton(this, 2);
		button3 = new CustomButton(this, 3);
		button4 = new CustomButton(this, 4);
		button5 = new CustomButton(this, 5);
		button6 = new CustomButton(this, 6);
		button7 = new CustomButton(this, 7);
		button8 = new CustomButton(this, 8);
		button9 = new CustomButton(this, 9);
		button10 = new CustomButton(this, 10);
		button11 = new CustomButton(this, 11);
		button12 = new CustomButton(this, 12);
	}
	
	/**
	 * Returns true if a given axis is above the move threshold.
	 *
	 * @param axis
	 * @return
	 */
	public boolean active(int axis) {
		if (axis == CustomJoystick.X_AXIS) {
			return Math.abs(super.getX()) > deadzone;
		} else if (axis == CustomJoystick.Y_AXIS) {
			return Math.abs(super.getY()) > deadzone;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if the joystick is actually connected.
	 * It determines this by counting the number of buttons
	 * (more than 0 means the joystick is connected).
	 *
	 * @return
	 */
	public boolean connected() {
		return DriverStation.getInstance().getStickButtonCount(port) > 0;
	}
	
	/**
	 * Returns the value of the given axis.
	 */
	@Override
	public double getAxis(int axis) {
		if (Math.abs(super.getRawAxis(axis)) < deadzone) {
			return 0.0;
		}
		return super.getRawAxis(axis);
	}
	
	public void setDeadzone(double deadzone) {
		this.deadzone = deadzone;
	}
}
