// WAS PID SOURCE
package org.usfirst.frc4904.standard.custom.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
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
    protected int getYawCalls;
    protected static final double MAX_DEGREES_PER_TICK = 90.0;
    protected static final double MAX_DEGREES_PER_SECOND_PER_TICK = 180;

    public NavX(SerialPort.Port port) {
        super(port);
        super.zeroYaw();
        lastYaw = 0.0f;
        lastPitch = 0.0f;
        lastRoll = 0.0f;
        getYawCalls = 0;
    }

    public NavX(I2C.Port port) {
        super(port);
        super.zeroYaw();
        lastYaw = 0.0f;
        lastPitch = 0.0f;
        lastRoll = 0.0f;
        getYawCalls = 0;
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
     * Returns an always positive yaw. Ignores anomalous values
     */
    public float getSafeYaw() {
        float yaw = super.getYaw();
        // SmartDashboard.putNumber("navx_yaw", yaw);
        // SmartDashboard.putNumber("navx_last_yaw", lastYaw);
        if ((Math.abs(yaw - lastYaw) > NavX.MAX_DEGREES_PER_TICK)
                && (Math.abs(Math.abs(yaw - lastYaw) - 360) > NavX.MAX_DEGREES_PER_TICK)) { // Smoothing
            return lastYaw;
        }
        lastYaw = yaw;
        return yaw;
    }

    @Override
    public float getYaw() {
        getYawCalls += 1;
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

    @Override
    public void zeroYaw() {
        super.zeroYaw();
        lastYaw = 0;
    }
}

