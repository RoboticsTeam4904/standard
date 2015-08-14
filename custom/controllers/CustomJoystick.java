package org.usfirst.frc4904.cmdbased.custom.controllers;


import org.usfirst.frc4904.cmdbased.InPipable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class CustomJoystick extends Joystick implements InPipable {
	public SuperButton[] buttons = new SuperButton[12];
	public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	private static final double moveThreshold = 0.05;
	private final int port;
	
	protected CustomJoystick(int port) {
		super(port);
		this.port = port;
		for (int i = 0; i < 12; i++) {
			buttons[i] = new SuperButton(this, i + 1); // Initialize all the buttons. Remember the index is one less than the button num
		}
	}
	
	public boolean active(int axis) {
		if (axis == X_AXIS) {
			return super.getX() > moveThreshold;
		} else if (axis == Y_AXIS) {
			return super.getY() > moveThreshold;
		} else {
			return false;
		}
	}
	
	public boolean connected() {
		return DriverStation.getInstance().getStickButtonCount(port) > 0;
	}
	
	/**
	 * Read values from Joystick
	 */
	public double[] readPipe() {
		return new double[] {this.getX(), this.getY(), this.getTwist()};
	}
	
	/**
	 * CustomJoystick always returns X, Y, twist
	 */
	public void setPipe(int mode) {}
}
