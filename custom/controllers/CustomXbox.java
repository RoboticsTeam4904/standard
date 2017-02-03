package org.usfirst.frc4904.standard.custom.controllers;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * An XboxController that implements the generic controller class.
 *
 */
public class CustomXbox extends XboxController implements Controller {
	// public enum Axis {
	// LEFT_X, LEFT_Y, RIGHT_X, RIGHT_Y, LEFT_TRIGGER, RIGHT_TRIGGER;
	// private double deadzone = CustomXbox.DEFAULT_DEADZONE;
	// }
	private final static int LEFT_X_AXIS = 0;
	private final static int LEFT_Y_AXIS = 1;
	private final static int RIGHT_X_AXIS = 2;
	private final static int RIGHT_Y_AXIS = 3;
	private final static int LEFT_TRIGGER_AXIS = 4;
	private final static int RIGHT_TRIGGER_AXIS = 5;
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
	public CustomButton a;
	public CustomButton b;
	public CustomButton x;
	public CustomButton y;
	public CustomButton leftBumper;
	public CustomButton rightBumper;
	public CustomButton back;
	public CustomButton start;
	public CustomButton leftStickButton;
	public CustomButton rightStickButton;
	public Stick leftStick;
	public Stick rightStick;
	public AnalogInput leftTrigger;
	public AnalogInput rightTrigger;
	public DPad dPad;

	public CustomXbox(final int port) {
		super(port);
		a = new CustomButton(this, CustomXbox.A_BUTTON_ID);
		b = new CustomButton(this, CustomXbox.B_BUTTON_ID);
		x = new CustomButton(this, CustomXbox.X_BUTTON_ID);
		y = new CustomButton(this, CustomXbox.Y_BUTTON_ID);
		leftBumper = new CustomButton(this, CustomXbox.LB_BUTTON_ID);
		rightBumper = new CustomButton(this, CustomXbox.RB_BUTTON_ID);
		back = new CustomButton(this, CustomXbox.BACK_BUTTON_ID);
		start = new CustomButton(this, CustomXbox.START_BUTTON_ID);
		leftStickButton = new CustomButton(this, CustomXbox.LEFT_THUMBSTICK_BUTTON_ID);
		rightStickButton = new CustomButton(this, CustomXbox.RIGHT_THUMBSTICK_BUTTON_ID);
		leftStick = new Stick(this, CustomXbox.LEFT_X_AXIS, CustomXbox.LEFT_Y_AXIS);
		rightStick = new Stick(this, CustomXbox.RIGHT_X_AXIS, CustomXbox.RIGHT_Y_AXIS);
		leftTrigger = new AnalogInput(this, CustomXbox.LEFT_TRIGGER_AXIS);
		rightTrigger = new AnalogInput(this, CustomXbox.RIGHT_TRIGGER_AXIS);
		dPad = new DPad(this);
	}

	public static class AnalogInput {
		public static final double DEFAULT_DEADZONE = 0.05;
		protected double deadzone = AnalogInput.DEFAULT_DEADZONE;
		protected CustomXbox controller;
		protected final int xIndex;

		private AnalogInput(CustomXbox controller, int index) {
			this.controller = controller;
			xIndex = index;
		}

		public double getX() {
			return controller.getAxis(xIndex);
		}

		public double getDeadzone() {
			return deadzone;
		}

		public void setDeadzone(double deadzone) {
			this.deadzone = deadzone;
		}
	}

	public static class Stick extends AnalogInput {
		protected final int yIndex;

		private Stick(CustomXbox controller, int xIndex, int yIndex) {
			super(controller, xIndex);
			this.yIndex = yIndex;
		}

		public double getY() {
			return controller.getAxis(yIndex);
		}
	}

	public static class DPad {
		public final Button up;
		public final Button upRight;
		public final Button right;
		public final Button downRight;
		public final Button down;
		public final Button downLeft;
		public final Button left;
		public final Button upLeft;

		public DPad(CustomXbox controller) {
			up = new DPadButton(controller, 0);
			upRight = new DPadButton(controller, 45);
			right = new DPadButton(controller, 90);
			downRight = new DPadButton(controller, 135);
			down = new DPadButton(controller, 180);
			downLeft = new DPadButton(controller, 225);
			left = new DPadButton(controller, 270);
			upLeft = new DPadButton(controller, 315);
		}
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
	 * Set the deadzone of the sticks
	 * 
	 * @param deadzone
	 *        the deadzone
	 */
	public void setStickDeadzone(double deadzone) {
		leftStick.setDeadzone(deadzone);
		rightStick.setDeadzone(deadzone);
	}

	/**
	 * Set the deadzone of the triggers
	 * 
	 * @param deadzone
	 *        the deadzone
	 */
	public void setTriggerDeadzone(double deadzone) {
		leftTrigger.setDeadzone(deadzone);
		rightTrigger.setDeadzone(deadzone);
	}

	/**
	 * Make one side of the controller vibrate
	 * 
	 * @param hand
	 *        The side of the controller to rumble
	 * @param intensity
	 *        How strong the rumble is
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
	 *        How strong the rumble is
	 */
	public void setRumble(double intensity) {
		final float amount = new Float(intensity);
		this.setRumble(RumbleType.kLeftRumble, amount);
		this.setRumble(RumbleType.kRightRumble, amount);
	}

	@Override
	public double getAxis(int axis) {
		return super.getRawAxis(axis);
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
