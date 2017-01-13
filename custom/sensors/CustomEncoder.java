package org.usfirst.frc4904.standard.custom.sensors;


/**
 * This is an extremely minimal encoder
 * that can be either a normal encoder or a
 * CAN encoder.
 *
 */
public interface CustomEncoder extends PIDSensor {
	/**
	 * Gets current count
	 */
	int get() throws InvalidSensorException;
	
	/**
	 * Gets current count
	 * This does not throw an exception on error! Use with caution!
	 */
	int getSafely();

	/**
	 * Gets current distance
	 */
	double getDistance() throws InvalidSensorException;

	/**
	 * Gets current distance
	 * This does not throw an exception on error! Use with caution!
	 */
	double getDistanceSafely();

	/**
	 * Gets direction of most recent movement
	 */
	boolean getDirection() throws InvalidSensorException;

	/**
	 * Gets direction of most recent movement
	 * This does not throw an exception on error! Use with caution!
	 */
	boolean getDirectionSafely();

	/**
	 * Returns true when stopped
	 */
	boolean getStopped() throws InvalidSensorException;

	/**
	 * Returns true when stopped
	 * This does not throw an exception on error! Use with caution!
	 */
	boolean getStoppedSafely();

	/**
	 * Gets rate
	 */
	double getRate() throws InvalidSensorException;

	/**
	 * Gets rate
	 * This does not throw an exception on error! Use with caution!
	 */
	double getRateSafely();

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