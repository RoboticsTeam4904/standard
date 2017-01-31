package org.usfirst.frc4904.standard.custom.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * An XboxController that implements the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	public final static int LEFT_X_AXIS = 0;
	public final static int LEFT_Y_AXIS = 1;
	public final static int RIGHT_X_AXIS = 2;
	public final static int RIGHT_Y_AXIS = 3;
	public final static int LEFT_TRIGGER_AXIS = 4;
	public final static int RIGHT_TRIGGER_AXIS = 5;
	protected static final int A_BUTTON_ID = 1;
	protected static final int B_BUTTON_ID = 2;
	protected static final int X_BUTTON_ID = 3;
	protected static final int Y_BUTTON_ID = 4;
	protected static final int LB_BUTTON_ID = 5;
	protected static final int RB_BUTTON_ID = 6;
	protected static final int BACK_BUTTON_ID = 7;
	protected static final int START_BUTTON_ID = 8;
	protected static final int LEFT_THUMBSTICK_BUTTON_ID = 9;
	protected static final int RIGHT_THUMBSTICK_BUTTON_ID = 10;
	private double THUMBSTICK_DEADZONE = 0.1;
	private double TRIGGER_DEADZONE = 0.01;
	public CustomButton a;
	public CustomButton b;
	public CustomButton x;
	public CustomButton y;
	public CustomButton lb;
	public CustomButton rb;
	public CustomButton back;
	public CustomButton start;
	public CustomButton lstickbutton;
	public CustomButton rstickbutton;
	public final Button up;
	public final Button upRight;
	public final Button right;
	public final Button downRight;
	public final Button down;
	public final Button downLeft;
	public final Button left;
	public final Button upLeft;

	public CustomXbox(final int port) {
		super(port);
		a = new CustomButton(this, CustomXbox.A_BUTTON_ID);
		b = new CustomButton(this, CustomXbox.B_BUTTON_ID);
		x = new CustomButton(this, CustomXbox.X_BUTTON_ID);
		y = new CustomButton(this, CustomXbox.Y_BUTTON_ID);
		lb = new CustomButton(this, CustomXbox.LB_BUTTON_ID);
		rb = new CustomButton(this, CustomXbox.RB_BUTTON_ID);
		back = new CustomButton(this, CustomXbox.BACK_BUTTON_ID);
		start = new CustomButton(this, CustomXbox.START_BUTTON_ID);
		lstickbutton = new CustomButton(this, CustomXbox.LEFT_THUMBSTICK_BUTTON_ID);
		rstickbutton = new CustomButton(this, CustomXbox.RIGHT_THUMBSTICK_BUTTON_ID);
		up = new DPadButton(this, 0);
		upRight = new DPadButton(this, 45);
		right = new DPadButton(this, 90);
		downRight = new DPadButton(this, 135);
		down = new DPadButton(this, 180);
		downLeft = new DPadButton(this, 225);
		left = new DPadButton(this, 270);
		upLeft = new DPadButton(this, 315);
	}

	/**
	 * Axis 0: left joystick x Axis 1: left joystick y Axis 2: right joystick x
	 * Axis 2: right joystick y
	 */

	public static double computeDeadzone(double input, double deadzone) {
		if (deadzone < 0 || deadzone >= 1) {
			deadzone = 0; // Prevent any weird errors
		}
		double result = Math.signum(input) * ((Math.abs(input) / (1 - deadzone)) + deadzone);
		return result;
	}

	/**
	 * Get the adjusted thumbstick deadzone
	 * 
	 * @return deadzone
	 */
	public double getThumbstickDeadzone() {
		return THUMBSTICK_DEADZONE;
	}

	/**
	 * Get the adjusted trigger deadzone
	 * 
	 * @return deadzone
	 */
	public double getTriggerDeadzone() {
		return TRIGGER_DEADZONE;
	}

	/**
	 * Set the deadzone of the thumbsticks
	 * 
	 * @param deadzone
	 */
	public void setThumbstickDeadzone(double deadzone) {
		THUMBSTICK_DEADZONE = deadzone;
	}

	/**
	 * Set the deadzone of the thumbsticks
	 * 
	 * @param deadzone
	 */
	public void setTriggerDeadzone(double deadzone) {
		TRIGGER_DEADZONE = deadzone;
	}

	/**
	 * Make one side of the controller vibrate
	 * 
	 * @param hand
	 *            The side of the controller to rumble
	 * @param intensity
	 *            How strong the rumble is
	 */
	public void setRumble(Hand hand, double intensity) {
		final float amount = new Float(intensity);
		if (hand == Hand.kLeft) {
			this.setRumble(RumbleType.kLeftRumble, amount);
		} else {
			this.setRumble(RumbleType.kRightRumble, amount);
		}
	}

	/**
	 * Make the controller vibrate
	 * 
	 * @param intensity
	 *            How strong the rumble is
	 */
	public void setRumble(double intensity) {
		final float amount = new Float(intensity);
		this.setRumble(RumbleType.kLeftRumble, amount);
		this.setRumble(RumbleType.kRightRumble, amount);
	}

	@Override
	public double getAxis(int axis) {
		switch (axis) {
		case LEFT_X_AXIS:
			return CustomXbox.computeDeadzone(this.getX(Hand.kLeft), THUMBSTICK_DEADZONE);
		case LEFT_Y_AXIS:
			return CustomXbox.computeDeadzone(this.getY(Hand.kLeft), THUMBSTICK_DEADZONE);
		case RIGHT_X_AXIS:
			return CustomXbox.computeDeadzone(this.getX(Hand.kRight), THUMBSTICK_DEADZONE);
		case RIGHT_Y_AXIS:
			return CustomXbox.computeDeadzone(this.getY(Hand.kRight), THUMBSTICK_DEADZONE);
		case LEFT_TRIGGER_AXIS:
			return CustomXbox.computeDeadzone(getTriggerAxis(Hand.kLeft), TRIGGER_DEADZONE);
		case RIGHT_TRIGGER_AXIS:
			return CustomXbox.computeDeadzone(getTriggerAxis(Hand.kRight), TRIGGER_DEADZONE);
		default:
			return super.getRawAxis(axis);
		}
	}

	public static class DPadButton extends Button {
		/* Instance Values */
		private final int direction;
		private final XboxController parent;

		/**
		 * Constructor
		 * 
		 * @param parent
		 * @param dPad
		 */
		DPadButton(final XboxController parent, final int dPadDirection) {
			this.parent = parent;
			direction = dPadDirection;
		}

		/* Extended Methods */
		@Override
		public boolean get() {
			return parent.getPOV(0) == direction;
		}
	}
}
