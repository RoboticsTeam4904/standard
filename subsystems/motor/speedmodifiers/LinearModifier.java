package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


/**
 * A speed modifier that changes the speed proportionally by a constant.
 * This can be used to scale motors, but their range will be reduced.
 */
public class LinearModifier implements SpeedModifier {
	protected final double scale;
	
	public LinearModifier(double scale) {
		this.scale = scale;
	}
	
	public LinearModifier() {
		this(1.0);
	}
	
	@Override
	public double modify(double speed) {
		return speed * scale;
	}
}
