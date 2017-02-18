package org.usfirst.frc4904.standard.custom.sensors;


/**
 * NativeDerivativeSensor represents a sensor which supports
 * native derivative calculations. This should, but does not
 * have to, be implemented alongside an actual sensor.
 * <p>
 * This interface serves only to differentiate between regular
 * sensors and ones that support native derivative calculations.
 * No extra work must be done to add this into an existing sensor.
 *
 * @see PIDSensor
 */
public interface NativeDerivativeSensor {
	/**
	 * Gets rate
	 *
	 * @warning does not indicate sensor errors
	 */
	double getRate();

	/**
	 * Gets rate
	 */
	double getRateSafely() throws InvalidSensorException;
}
