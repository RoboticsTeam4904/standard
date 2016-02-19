package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


/**
 * A SpeedModifier that changes nothing.
 * It extends LinearModifier to reduce code duplication.
 */
public class IdentityModifier extends LinearModifier {
	/**
	 *
	 * A SpeedModifier that changes nothing.
	 * It extends LinearModifier to reduce code duplication.
	 */
	public IdentityModifier() {
		super(1);
	}
}
