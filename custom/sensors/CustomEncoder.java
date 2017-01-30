package org.usfirst.frc4904.standard.custom.sensors;


/**
 * This is an extremely minimal encoder
 * that can be either a normal encoder or a
 * CAN encoder.
 *
 */
public interface CustomEncoder extends PIDSensor, NativeDerivativeSensor {
	/**
	 * Gets current count
	 *
	 * @warning does not indicate sensor errors
	 */
	int get();

	/**
	 * Gets current count
	 */
	int getSafely() throws InvalidSensorException;

	/**
	 * Gets current distance
	 *
	 * @warning does not indicate sensor errors
	 */
	double getDistance();

	/**
	 * Gets current distance
	 */
	double getDistanceSafely() throws InvalidSensorException;

	/**
	 * Gets direction of most recent movement
	 *
	 * @warning does not indicate sensor errors
	 */
	boolean getDirection();

	/**
	 * Gets direction of most recent movement
	 */
	boolean getDirectionSafely() throws InvalidSensorException;

	/**
	 * Returns true when stopped
	 *
	 * @warning does not indicate sensor errors
	 */
	boolean getStopped();

	/**
	 * Returns true when stopped
	 */
	boolean getStoppedSafely() throws InvalidSensorException;

	/**
	 * Gets the distance per pulse
	 */
	double getDistancePerPulse();

	/**
	 * Sets the distance per pulse
	 */
	void setDistancePerPulse(double distancePerPulse);

	/**
	 * Gets inversion state
	 */
	boolean getReverseDirection();

	/**
	 * To the surprise of everyone, enables and disables reverse direction
	 */
	void setReverseDirection(boolean reverseDirection);

	/**
	 * Resets the encoder
	 */
	void reset();
}