package org.usfirst.frc4904.standard.custom.sensors;

import org.usfirst.frc4904.standard.custom.CustomPIDSourceType;

public interface IMU {
	/**
	 * Resets the IMU.
	 */
	public void reset();

	/**
	 * @return Rate of rotation about yaw axis
	 */
	public double getRate();

	/**
	 * @return Current yaw value
	 */
	public float getYaw();

	/**
	 * @return Current pitch value
	 */
	public float getPitch();

	/**
	 * @return Current roll value
	 */
	public float getRoll();

	public void setCustomPIDSourceType(CustomPIDSourceType kdisplacement);

}
