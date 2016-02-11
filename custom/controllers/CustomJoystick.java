package org.usfirst.frc4904.standard.custom.controllers;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

/**
 * A joystick that implements the generic
 * controller interface.
 * This allows us to use a joystick as a
 * controller.
 * This contains 12 buttons to reflect
 * the joysticks we are typically using.
 *
 */
public class CustomJoystick extends Joystick implements Controller {
	public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	private static final int NUM_BUTTONS = 12;
	private static final double moveThreshold = 0.05;
	private final int port;
	// Buttons
	public final SuperButton button1;
	public final SuperButton button2;
	public final SuperButton button3;
	public final SuperButton button4;
	public final SuperButton button5;
	public final SuperButton button6;
	public final SuperButton button7;
	public final SuperButton button8;
	public final SuperButton button9;
	public final SuperButton button10;
	public final SuperButton button11;
	public final SuperButton button12;
	
	public CustomJoystick(int port) {
		super(port);
		this.port = port;
		button1 = new SuperButton(this, 1);
		button2 = new SuperButton(this, 2);
		button3 = new SuperButton(this, 3);
		button4 = new SuperButton(this, 4);
		button5 = new SuperButton(this, 5);
		button6 = new SuperButton(this, 6);
		button7 = new SuperButton(this, 7);
		button8 = new SuperButton(this, 8);
		button9 = new SuperButton(this, 9);
		button10 = new SuperButton(this, 10);
		button11 = new SuperButton(this, 11);
		button12 = new SuperButton(this, 12);
	}
	
	/**
	 * Returns true if a given axis is
	 * above the move threshold.
	 * 
	 * @param axis
	 * @return
	 */
	public boolean active(int axis) {
		if (axis == X_AXIS) {
			return super.getX() > moveThreshold;
		} else if (axis == Y_AXIS) {
			return super.getY() > moveThreshold;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if the joystick
	 * is actually connected. It
	 * determines this by counting
	 * the number of buttons (> 0
	 * means the joystick is
	 * connected).
	 * 
	 * @return
	 */
	public boolean connected() {
		return DriverStation.getInstance().getStickButtonCount(port) > 0;
	}
	
	/**
	 * Returns the value of the
	 * given axis.
	 */
	public double getAxis(int axis) {
		return super.getRawAxis(axis);
	}
}
