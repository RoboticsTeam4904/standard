package org.usfirst.frc4904.standard.custom.controllers;


public class CustomXbox extends XboxController implements Controller {
	public CustomXbox(final int port) {
		super(port);
	}
	
	public double getAxis(int axis) {
		switch (axis) {
			case 0:
				return super.getRawAxis(0);
			case 1:
				return super.getRawAxis(1);
			case 2:
				return super.getRawAxis(4);
			default:
				return super.getRawAxis(axis);
		}
	}
}
