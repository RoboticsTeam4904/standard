package org.usfirst.frc4904.standard.custom.sensors;

import org.usfirst.frc4904.standard.custom.CustomPIDSourceType;

public interface PIDSensor {
	/**
	 * Set which parameter of the device you are using as a process control
	 * variable.
	 *
	 * @param pidSource An enum to select the parameter.
	 */
	public void setCustomPIDSourceType(CustomPIDSourceType pidSource);

	/**
	 * Get which parameter of the device you are using as a process control
	 * variable.
	 *
	 * @return the currently selected PID source parameter
	 */
	public CustomPIDSourceType getCustomPIDSourceType();

	/**
	 * Get the result to use in PIDController $
	 *
	 * @return the result to use in PIDController
	 * @warning does not report sensor errors, will just return 0
	 */
	public double pidGet();

	/**
	 * Get the result to use in PIDController $
	 *
	 * @return the result to use in PIDController
	 * @throws InvalidSensorException when sensor data should not be used for PID
	 *                                due to potential inaccuracy
	 */
	public double pidGetSafely() throws InvalidSensorException;
}