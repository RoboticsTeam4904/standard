package org.usfirst.frc4904.standard.custom.controllers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class CustomDrivingJoystick extends CommandJoystick {
    public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	public static final int SLIDER_AXIS = 3;
	protected final double deadzone;
	protected final int port;
	// Buttons
	public final JoystickButton button1;
	public final JoystickButton button2;
    public final Joystick m_hid;

	public CustomDrivingJoystick(int port, double deadzone) {
		super(port);
		if (deadzone < 0 || deadzone > 1) {
			throw new IllegalArgumentException("Joystick deadzone must be in [0, 1]");
		}
		this.deadzone = deadzone;
		this.port = port;
		deadzone = 0;
        m_hid = new Joystick(port);
		button1  = new JoystickButton(m_hid, 1);
		button2  = new JoystickButton(m_hid, 2);
	}

	/**
	 * Returns true if a given axis is above the move threshold.
	 *
	 * @param axis
	 * @return	whether the current value of that axis is outside of the deadzone
	 */
	public boolean active(int axis) { //unused, maybe remove?
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
