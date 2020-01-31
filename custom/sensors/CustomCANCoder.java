package org.usfirst.frc4904.standard.custom.sensors;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.CustomPIDSourceType;

public class CustomCANCoder extends CANCoder implements CustomEncoder {
    protected static final double DEFAULT_DISTANCE_PER_PULSE = 1.0;
    protected static final CustomPIDSourceType DEFAULT_SOURCE_TYPE = CustomPIDSourceType.kDisplacement;
    protected static final boolean DEFAULT_REVERSE_DIRECTION = false;
    protected CustomPIDSourceType sensorType;
    protected double distancePerPulse;
    protected boolean reverseDirection;
    // protected CANCoderConfiguration config;
    


    public CustomCANCoder(int deviceNum, CustomPIDSourceType sensorType, boolean reverseDirection, double distancePerPulse) {
        super(deviceNum);
        // config = new CANCoderConfiguration();
        // this.configAllSettings(config);
        // this.configSensorDirection(bSensorDirection);
        setReverseDirection(reverseDirection);
        setDistancePerPulse(distancePerPulse);
        setCustomPIDSourceType(sensorType);
    }

    public CustomCANCoder(int deviceNum, CustomPIDSourceType sensorType, boolean reverseDirection) {
        this(deviceNum, sensorType, reverseDirection, DEFAULT_DISTANCE_PER_PULSE);
    }

    public CustomCANCoder(int deviceNum, CustomPIDSourceType sensorType) {
        this(deviceNum, sensorType, DEFAULT_REVERSE_DIRECTION);
    }

    public CustomCANCoder(int deviceNum) {
        this(deviceNum, DEFAULT_SOURCE_TYPE);
    }

    public CustomPIDSourceType getCustomPIDSourceType() {
        return sensorType;
    }

    public void setCustomPIDSourceType (CustomPIDSourceType sensorType) {
        this.sensorType = sensorType;
    }

    public void setDistancePerPulse (double pulse) {
        distancePerPulse = pulse;
    }

    @Override
    public double pidGet() {
        return get();
    }

    @Override
    public double pidGetSafely() throws InvalidSensorException {
        return getSafely();
    }

    @Override
    public double getRate() {
        try {
			return getRateSafely();
		} catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
    }

    @Override
    public double getRateSafely() throws InvalidSensorException {
        if (reverseDirection) {
            return getVelocity() * distancePerPulse * -1.0;
        }
        else {
            return getVelocity() * distancePerPulse;
        }

    }

    @Override
    public int get() {
        try {
			return getSafely();
		} catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
    }

    @Override
    public int getSafely() throws InvalidSensorException {
        if (getCustomPIDSourceType() == CustomPIDSourceType.kDisplacement) {
            return (int) getDistance();
        }
        return (int) getRate();
    }

    @Override
    public double getDistance() {
        try {
			return getDistanceSafely();
		} catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
    }

    @Override
    public double getDistanceSafely() throws InvalidSensorException {
        if (reverseDirection) {
            return getPosition() * distancePerPulse * -1.0;
        }
        else {
            return getPosition() * distancePerPulse;
        }
    }

    @Override
    public boolean getDirection() {
        try {
			return getDirectionSafely();
		} catch (Exception e) {
			LogKitten.ex(e);
			return false;
		}
    }

    @Override
    public boolean getDirectionSafely() throws InvalidSensorException {
        return !reverseDirection == (getPosition() >= 0);
    }

    @Override
    public boolean getStopped() {
        try {
			return getStoppedSafely();
		} catch (Exception e) {
			LogKitten.ex(e);
			return false;
		}
    }

    @Override
    public boolean getStoppedSafely() throws InvalidSensorException {
        return Util.isZero(getRate());
    }

    @Override
    public double getDistancePerPulse() {
        return distancePerPulse;
    }

    @Override
    public boolean getReverseDirection() {
       return reverseDirection;
    }

    @Override
    public void setReverseDirection(boolean reverseDirection) {
        this.reverseDirection = reverseDirection;

    }

    @Override
    public void reset() {
        setPosition(0.0);

    }


}