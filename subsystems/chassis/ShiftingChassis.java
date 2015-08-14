package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import edu.wpi.first.wpilibj.Solenoid;

public interface ShiftingChassis {
	/**
	 * @return an array of Solenoids (should return from left to right)
	 */
	public Solenoid[] getSolenoids();
}
