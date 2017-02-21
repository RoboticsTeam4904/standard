package org.usfirst.frc4904.standard.custom.controllers;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class TeensyController extends Joystick implements Controller {
	public int numButtons;
	public final int port;
	public CustomButton[] buttons; // array of all buttons

	public TeensyController(int port, int numButtons) {
		super(port);
		this.port = port;
		this.numButtons = numButtons;
		CustomButton[] buttons = new CustomButton[numButtons]; // array of any length
		this.buttons = buttons;
		for (int index = 0; index < numButtons; index++) { // sets each button in buttons
			buttons[index] = new CustomButton(this, index + 1);
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

	@Override
	public double getAxis(int axis) { // gets the value of the axis
		return super.getRawAxis(axis);
	}

	public double getNumButtons() { // returns number of buttons
		return numButtons;
	}

	public CustomButton getButton(int id) { // allows for indirect access to array
		return buttons[id];
	}
}
