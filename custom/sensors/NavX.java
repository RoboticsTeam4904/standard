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
	private double lastYawRate;
	
	public NavX(SerialPort.Port port) {
		super(port);
		super.zeroYaw();
		lastYaw = 0.0f;
		lastPitch = 0.0f;
		lastRoll = 0.0f;
	}
	
	public double pidGet() {
		return getYaw();
	}
	
	public double getRate() {
		double rate = super.getRate();
		if (Math.abs(rate) > Math.abs(lastYawRate) + 90.0) {
			return lastYawRate;
		}
		lastYawRate = rate;
		return rate;
	}
	
	/**
	 * Returns an always positive yaw
	 */
	public float getYaw() {
		float yaw = super.getYaw();
		if (Math.abs(yaw) > Math.abs(lastYaw) + 90.0) { // Smoothing
			return lastYaw;
		}
		lastYaw = yaw;
		return yaw;
	}
	
	/**
	 * Returns an always positive pitch
	 */
	public float getPitch() {
		float pitch = super.getPitch();
		if (Math.abs(pitch) > Math.abs(lastPitch) + 90.0) {
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
		if (Math.abs(roll) > Math.abs(lastRoll) + 90.0) {
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
