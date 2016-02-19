package org.usfirst.frc4904.standard.subsystems.chassis;


/**
 * An interface that can be attached to a chassis to allow it to return
 * a pair of shifters to shift the chassis into a high gear and low gear configuration.
 */
public interface ShiftingChassis {
	/**
	 * @return an array of Solenoids (should return from left to right)
	 */
	public SolenoidShifters getShifter();
}
