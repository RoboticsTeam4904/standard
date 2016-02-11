package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


public class LinearModifier implements SpeedModifier {
	protected final double scale;
	
	public LinearModifier(double scale) {
		this.scale = scale;
	}
	
	public LinearModifier() {
		this(1.0);
	}
	
	public double modify(double speed) {
		return speed * scale;
	}
}
