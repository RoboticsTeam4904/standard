package org.usfirst.frc4904.standard.custom.controllers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * A joystick that implements the generic controller interface and the 2023
 * trigger interface. This allows us to use a joystick as a controller. This
 * contains 12 buttons to reflect the joysticks we are typically using.
 * 
 * TO DO: should probably extend or be replaced with https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/CommandJoystick.html
 */
public class CustomCommandJoystick extends Joystick {
	public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	public static final int SLIDER_AXIS = 3;
	protected final double deadzone;
	protected final int port;
	// Buttons
	public final JoystickButton button1;
	public final JoystickButton button2;
	public final JoystickButton button3;
	public final JoystickButton button4;
	public final JoystickButton button5;
	public final JoystickButton button6;
	public final JoystickButton button7;
	public final JoystickButton button8;
	public final JoystickButton button9;
	public final JoystickButton button10;
	public final JoystickButton button11;
	public final JoystickButton button12;

	public CustomCommandJoystick(int port, double deadzone) {
		super(port);
		if (deadzone < 0 || deadzone > 1) {
			throw new IllegalArgumentException("Joystick deadzone must be in [0, 1]");
		}
		this.deadzone = deadzone;
		this.port = port;
		deadzone = 0;
		button1  = new JoystickButton(this, 1);
		button2  = new JoystickButton(this, 2);
		button3  = new JoystickButton(this, 3);
		button4  = new JoystickButton(this, 4);
		button5  = new JoystickButton(this, 5);
		button6  = new JoystickButton(this, 6);
		button7  = new JoystickButton(this, 7);
		button8  = new JoystickButton(this, 8);
		button9  = new JoystickButton(this, 9);
		button10 = new JoystickButton(this, 10);
		button11 = new JoystickButton(this, 11);
		button12 = new JoystickButton(this, 12);
	}

	/**
	 * Returns true if a given axis is above the move threshold.
	 *
	 * @param axis
	 * @return	whether the current value of that axis is outside of the deadzone
	 */
	public boolean active(int axis) {
		return Math.abs(getAxis(axis)) > deadzone;
		// if (axis == CustomCommandJoystick.X_AXIS) {
		// 	return Math.abs(super.getX()) > deadzone;
		// } else if (axis == CustomCommandJoystick.Y_AXIS) {
		// 	return Math.abs(super.getY()) > deadzone;
		// } else {
		// 	return false;
		// }
	}

	/**
	 * Returns true if the joystick is actually connected. It determines this by
	 * counting the number of buttons (more than 0 means the joystick is connected).
	 *
	 * @return
	 */
	public boolean connected() {
		return DriverStation.getStickButtonCount(port) > 0;
	}

	/**
	 * Returns the value of the given axis.
	 */
	public double getAxis(int axis) {
		double val = super.getRawAxis(axis);
		if (Math.abs(val) < deadzone) {
			return 0.0;
		}
		return (val - Math.signum(val)*deadzone)/(1-deadzone);	// linear between 0 and 1 in the remaining range
		// return super.getRawAxis(axis);
	}
}
