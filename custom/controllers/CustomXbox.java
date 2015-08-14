package org.usfirst.frc4904.cmdbased.custom.controllers;


public class CustomXbox extends XboxController implements Controller {
	private PipeModes mode;
	
	public CustomXbox(final int port) {
		super(port);
		mode = PipeModes.XYTwist;
	}
	
	public double[] readPipe() {
		switch (mode) {
			case All:
				return new double[] {this.leftStick.getX(), this.leftStick.getY(), this.rightStick.getX(), this.rightStick.getY()};
			case XYTwist:
				return new double[] {this.leftStick.getX(), this.leftStick.getY(), this.rightStick.getX()};
			case X:
				return new double[] {this.leftStick.getX()};
			case Y:
				return new double[] {this.leftStick.getY()};
			case Twist:
				return new double[] {this.rightStick.getX()};
			case Fourth:
				return new double[] {this.rightStick.getY()};
			default:
				return new double[] {this.leftStick.getX(), this.leftStick.getY(), this.rightStick.getX()};
		}
	}
	
	/**
	 * CustomXbox pipe modes:
	 * 0: leftStick X, leftStick Y, rightStick X
	 * 1: rightStick X, rightStick Y
	 * 2: leftStick X, leftStick Y, rightStick X, rightStick Y
	 */
	public void setPipe(Enum mode) {
		this.mode = (PipeModes) mode;
	}
}
