package org.usfirst.frc4904.standard.subsystems.motor.slopecontrollers;


public class Linear implements SlopeController {
	private final double scale;
	
	public Linear(double scale) {
		this.scale = scale;
	}
	
	public Linear() {
		this(1.0);
	}
	
	public double reslope(double speed) {
		return speed * scale;
	}
}
