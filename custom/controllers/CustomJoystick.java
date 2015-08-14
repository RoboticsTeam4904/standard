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
	private PipeModes mode;
	// Buttons
	public final Button button1;
	public final Button button2;
	public final Button button3;
	public final Button button4;
	public final Button button5;
	public final Button button6;
	public final Button button7;
	public final Button button8;
	public final Button button9;
	public final Button button10;
	public final Button button11;
	public final Button button12;
	
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
		mode = PipeModes.XYTwist;
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
		switch (mode) {
			case All:
			case XYTwist:
				return new double[] {this.getX(), this.getY(), this.getTwist()};
			case X:
				return new double[] {this.getX()};
			case Y:
				return new double[] {this.getY()};
			case Twist:
				return new double[] {this.getTwist()};
			case Fourth:
			default:
				return new double[] {this.getX(), this.getY(), this.getTwist()};
		}
	}
	
	public void setPipe(Enum mode) {
		this.mode = (PipeModes) mode;
	}
}
