package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


/**
 * A SpeedModifier that changes nothing.
 * It extends LinearModifier to reduce code duplication.
 */
public class IdentityModifier implements SpeedModifier {
	/**
	 * A SpeedModifier that changes nothing.
	 * It doesn't extend LinearModifier in order to reduce computation time
	 * and have the same code duplication.
	 */
	public double modify(double speed) {
		return speed;
	}
}
