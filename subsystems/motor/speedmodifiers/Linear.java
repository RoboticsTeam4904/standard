package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


public class Linear implements SpeedModifier {
	private final double scale;
	
	public Linear(double scale) {
		this.scale = scale;
	}
	
	public Linear() {
		this(1.0);
	}
	
	public double modify(double speed) {
		return speed * scale;
	}
}
