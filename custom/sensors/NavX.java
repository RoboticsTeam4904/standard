package org.usfirst.frc4904.standard.custom.sensors;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * Local NavX interface.
 *
 */
public class NavX extends AHRS implements IMU {
	protected float lastYaw;
	protected float lastPitch;
	protected float lastRoll;
	protected double lastYawRate;
	protected static final double MAX_DEGREES_PER_TICK = 90.0;
	protected static final double MAX_DEGREES_PER_SECOND_PER_TICK = 180;

	public NavX(SerialPort.Port port) {
		super(port);
		super.zeroYaw();
		lastYaw = 0.0f;
		lastPitch = 0.0f;
		lastRoll = 0.0f;
	}

	@Override
	public double pidGet() {
		if (getPIDSourceType() == PIDSourceType.kRate) {
			return getRate();
		} else {
			return getYaw();
		}
	}

	@Override
	public double getRate() {
		double rate = super.getRate();
		if (Math.abs(rate - lastYawRate) > NavX.MAX_DEGREES_PER_SECOND_PER_TICK) {
			return lastYawRate;
		}
		lastYawRate = rate;
		return rate;
	}

	/**
	 * Returns an always positive yaw
	 */
	@Override
	public float getYaw() {
		float yaw = super.getYaw();
		if (Math.abs(yaw - lastYaw) > NavX.MAX_DEGREES_PER_TICK) { // Smoothing
			return lastYaw;
		}
		lastYaw = yaw;
		return yaw;
	}

	public float getRawYaw() {
		return super.getYaw();
	}

	/**
	 * Returns an always positive pitch
	 */
	@Override
	public float getPitch() {
		float pitch = super.getPitch();
		if (Math.abs(pitch - lastPitch) > NavX.MAX_DEGREES_PER_TICK) {
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
	@Override
	public float getRoll() {
		float roll = super.getRoll();
		if (Math.abs(roll - lastRoll) > NavX.MAX_DEGREES_PER_TICK) {
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
