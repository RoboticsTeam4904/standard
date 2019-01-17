package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public interface PIDSensor {
	/**
	 * Set which parameter of the device you are using as a process control
	 * variable.
	 *
	 * @param pidSource
	 *        An enum to select the parameter.
	 */
	public void setPIDSourceType(PIDSourceType pidSource);

	/**
	 * Get which parameter of the device you are using as a process control
	 * variable.
	 *
	 * @return the currently selected PID source parameter
	 */
	public PIDSourceType getPIDSourceType();

	/**
	 * Get the result to use in PIDController
	 * $
	 *
	 * @return the result to use in PIDController
	 * @warning does not report sensor errors, will just return 0
	 */
	public double pidGet();

	/**
	 * Get the result to use in PIDController
	 * $
	 *
	 * @return the result to use in PIDController
	 * @throws InvalidSensorException
	 *         when sensor data should not be used for PID due to potential inaccuracy
	 */
	public double pidGetSafely() throws InvalidSensorException;

	/**
	 * Class to wrap a PIDSource to a PIDSensor
	 *
	 */
	public static class PIDSourceWrapper implements PIDSensor {
		PIDSource source;

		public PIDSourceWrapper(PIDSource source) {
			this.source = source;
		}
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			source.setPIDSourceType(pidSource);
		}
		
		@Override
		public PIDSourceType getPIDSourceType() {
			return source.getPIDSourceType();
		}
		
		@Override
		public double pidGetSafely() throws InvalidSensorException { // No exception possible anyway
			return source.pidGet();
		}
		
		@Override
		public double pidGet() {
			return source.pidGet();
		}
	}
}
