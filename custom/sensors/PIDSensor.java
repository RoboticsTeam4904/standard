package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.PIDSource;

public interface PIDSensor extends PIDSource {
	/**
	 * Get the result to use in PIDController
	 *
	 * @return the result to use in PIDController
	 * @throws InvalidSensorException
	 *         when sensor data should not be used for PID due to potential inaccuracy
	 */
	default public double pidGetSafely() throws InvalidSensorException {
		return pidGet();
	}
}
