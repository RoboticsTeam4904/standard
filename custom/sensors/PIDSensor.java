package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

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
		public double pidGet() {
			return source.pidGet();
		}
	}
}
