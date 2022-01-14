// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.sensors;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorTimeBase;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.CustomPIDSourceType;

public class CustomCANCoder extends CANCoder implements CustomEncoder {
    protected static final CustomPIDSourceType DEFAULT_SOURCE_TYPE = CustomPIDSourceType.kDisplacement;
    protected static final boolean DEFAULT_REVERSE_DIRECTION = false;
    protected CustomPIDSourceType sensorType;
    protected double distancePerPulse;
    protected boolean reverseDirection;
    protected CANCoderConfiguration config;

    public CustomCANCoder(int deviceNum, double distancePerPulse, CustomPIDSourceType sensorType,
            boolean reverseDirection) {
        super(deviceNum);
        config = new CANCoderConfiguration();
        super.configAllSettings(config);
        setDistancePerPulse(distancePerPulse);
        setCustomPIDSourceType(sensorType);
        setReverseDirection(reverseDirection);
        reset();
    }

    public CustomCANCoder(int deviceNum, double distancePerPulse, CustomPIDSourceType sensorType) {
        this(deviceNum, distancePerPulse, sensorType, DEFAULT_REVERSE_DIRECTION);
    }

    public CustomCANCoder(int deviceNum, double distancePerPulse) {
        this(deviceNum, distancePerPulse, DEFAULT_SOURCE_TYPE);
    }

    public CustomPIDSourceType getCustomPIDSourceType() {
        return sensorType;
    }

    public void setCustomPIDSourceType(CustomPIDSourceType sensorType) {
        this.sensorType = sensorType;
    }

    public void setDistancePerPulse(double pulse, SensorTimeBase collectionPeriod) {
        this.distancePerPulse = pulse;
        super.configFeedbackCoefficient(pulse, "meters", collectionPeriod);
    }

    public void setDistancePerPulse(double pulse) {
        setDistancePerPulse(pulse, SensorTimeBase.PerSecond);
    }

    @Override
    public double pidGet() {
        try {
            return pidGetSafely();
        } catch (Exception e) {
            LogKitten.ex(e);
            return 0;
        }
    }

    @Override
    public double pidGetSafely() throws InvalidSensorException {
        if (getCustomPIDSourceType() == CustomPIDSourceType.kDisplacement) {
            return getDistance();
        }
        return getRate();
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
        return getVelocity();

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
        return getPosition();
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
        super.configSensorDirection(!reverseDirection);

    }

    @Override
    public void reset() {
        setPosition(0.0);

    }

}
