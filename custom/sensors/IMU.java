package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.controller.PIDController;

public interface IMU extends PIDController {
	/**
	 * Resets the IMU.
	 */
	public void reset();

	/**
	 * @return
	 * 		Rate of rotation about yaw axis
	 */
	public double getRate();

	/**
	 * @return
	 * 		Current yaw value
	 */
	public float getYaw();

	/**
	 * @return
	 * 		Current pitch value
	 */
	public float getPitch();

	/**
	 * @return
	 * 		Current roll value
	 */
	public float getRoll();
}
