package org.usfirst.frc4904.cmdbased.custom.controllers;


public class CustomXbox extends XboxController implements Controller {
	private int pipeMode;
	
	public CustomXbox(final int port) {
		super(port);
		pipeMode = 0;
	}
	
	public double[] readPipe() {
		switch (pipeMode) {
			case 0:
				return new double[] {this.leftStick.getX(), this.leftStick.getY(), this.rightStick.getX()};
			case 1:
				return new double[] {this.rightStick.getX(), this.rightStick.getY()};
			case 2:
				return new double[] {this.leftStick.getX(), this.leftStick.getY(), this.rightStick.getX(), this.rightStick.getY()};
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
	public void setPipe(int mode) {
		pipeMode = mode;
	}
}
