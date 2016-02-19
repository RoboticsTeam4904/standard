package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


/**
 * An interface for SpeedModifiers.
 * A speed modifier changes the input value of the motor.
 * This class should be used for fairly simple modifications,
 * such as functions or usage of omnipresent sensors (e.g. the PDP).
 */
public interface SpeedModifier {
	/**
	 * This function actually changes the speed that the motor should use.
	 *
	 * @param speed
	 *        The input speed of the motor
	 * @return
	 * 		The new output speed of the motor
	 */
	double modify(double speed);
}
