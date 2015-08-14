package org.usfirst.frc4904.cmdbased.custom.controllers;


import org.usfirst.frc4904.cmdbased.InPipable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class CustomJoystick extends Joystick implements InPipable, Controller {
	public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	private static final int NUM_BUTTONS = 12;
	private static final double moveThreshold = 0.05;
	private final int port;
	// Buttons
	public static Button button1;
	public static Button button2;
	public static Button button3;
	public static Button button4;
	public static Button button5;
	public static Button button6;
	public static Button button7;
	public static Button button8;
	public static Button button9;
	public static Button button10;
	public static Button button11;
	public static Button button12;
	
	public CustomJoystick(int port) {
		super(port);
		this.port = port;
		button1 = new JoystickButton(this, 1);
		button2 = new JoystickButton(this, 2);
		button3 = new JoystickButton(this, 3);
		button4 = new JoystickButton(this, 4);
		button5 = new JoystickButton(this, 5);
		button6 = new JoystickButton(this, 6);
		button7 = new JoystickButton(this, 7);
		button8 = new JoystickButton(this, 8);
		button9 = new JoystickButton(this, 9);
		button10 = new JoystickButton(this, 10);
		button11 = new JoystickButton(this, 11);
		button12 = new JoystickButton(this, 12);
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
	
	public Button[] getButtons() {
		return new Button[] {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12};
	}
	
	/**
	 * CustomJoystick always returns X, Y, twist
	 */
	public void setPipe(int mode) {}
}
