package org.usfirst.frc4904.standard.custom.sensors;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * Local NavX interface.
 *
 */
public class NavX extends AHRS {
	private float lastYaw;
	private float lastPitch;
	private float lastRoll;
	
	public NavX(SerialPort.Port port) {
		super(port);
		super.zeroYaw();
		lastYaw = 0.0f;
		lastPitch = 0.0f;
		lastRoll = 0.0f;
	}
	
	/**
	 * Returns an always positive yaw
	 */
	public float getYaw() {
		float yaw = super.getYaw();
		if (Math.abs(yaw) > Math.abs(lastYaw * 2)) { // Smoothing
			return lastYaw;
		}
		if (yaw < 0) {
			lastYaw = 360 + yaw;
			return 360 + yaw;
		} else {
			lastYaw = yaw;
			return yaw;
		}
	}
	
	/**
	 * Returns an always positive pitch
	 */
	public float getPitch() {
		float pitch = super.getPitch();
		if (Math.abs(pitch) > Math.abs(lastPitch * 2)) {
			return lastPitch;
		}
		if (pitch < 0) {
			lastPitch = 360 + pitch;
			return 360 + pitch;
		} else {
			lastPitch = pitch;
			return pitch;
		}
	}
	
	/**
	 * Returns an always positive roll
	 */
	public float getRoll() {
		float roll = super.getRoll();
		if (Math.abs(roll) > Math.abs(lastRoll) * 2) {
			return lastRoll;
		}
		if (roll < 0) {
			lastRoll = 360 + roll;
			return 360 + roll;
		} else {
			lastRoll = roll;
			return roll;
		}
	}
}
