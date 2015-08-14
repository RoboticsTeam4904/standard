package org.usfirst.frc4904.cmdbased.custom;


import org.usfirst.frc4904.cmdbased.InPipable;
import edu.wpi.first.wpilibj.PIDSource;

public interface Gyro extends PIDSource, InPipable {
	/**
	 * Zero the yaw, pitch, and roll.
	 * Sets the current angles received by the Gyro to the "zero" angles,
	 * and subtracts them from future measurements.
	 */
	public void zero();
	
	/**
	 * Get the current orientation of the gyro relative to the zero point.
	 * 
	 * @return double[3] angles
	 *         a double array with 3 angles - the yaw, pitch, and roll in that order
	 */
	public double[] getAngles();
	
	/**
	 * Get the current yaw of the gyro relative to the zero point.
	 * 
	 * @return double yaw
	 */
	public double getYaw();
	
	/**
	 * Get the current pitch of the gyro relative to the zero point.
	 * 
	 * @return double pitch
	 */
	public double getPitch();
	
	/**
	 * Get the current roll of the gyro relative to the zero point.
	 * 
	 * @return double roll
	 */
	public double getRoll();
	
	/**
	 * Get the current orientation of the gyro (ignoring the zero point.)
	 * 
	 * @return double[3] trueAngles
	 *         a double array with 3 angles - the yaw, pitch, and roll in that order
	 */
	public double[] getTrueAngles();
	
	/**
	 * Get the current yaw of the gyro (ignoring the zero point.)
	 * 
	 * @return double yaw
	 */
	public double getTrueYaw();
	
	/**
	 * Get the current pitch of the gyro (ignoring the zero point.)
	 * 
	 * @return double pitch
	 */
	public double getTruePitch();
	
	/**
	 * Get the current roll of the gyro (ignoring the zero point.)
	 * 
	 * @return double roll
	 */
	public double getTrueRoll();
	
	/**
	 * This should return the most useful gyro data
	 * 
	 * @return useful data
	 */
	public double[] readPipe();
}