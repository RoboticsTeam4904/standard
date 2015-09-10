package org.usfirst.frc4904.standard.custom.controllers;


public class CustomXbox extends XboxController implements Controller {
	public CustomXbox(final int port) {
		super(port);
	}
	
	public double getAxis(int axis) {
		switch (axis) {
			case 0:
				return this.leftStick.getX();
			case 1:
				return this.leftStick.getY();
			case 2:
				return this.rightStick.getX();
			default:
				return super.getRawAxis(axis);
		}
	}
}
