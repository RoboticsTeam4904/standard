package org.usfirst.frc4904.standard.custom.sensors;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
// import com.ctre.phoenix.sensors.SensorTimeBase;
// TODO: what is sensor time base why is it gone

// import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;

public class CustomCANCoder extends CANcoder implements CustomEncoder {
    protected static final boolean DEFAULT_REVERSE_DIRECTION = false;
    protected double distancePerPulse;
    protected boolean reverseDirection;
    protected CANcoderConfiguration config;

    public CustomCANCoder(int deviceNum, double distancePerPulse,
            boolean reverseDirection) {
        super(deviceNum);
        config = new CANcoderConfiguration();
       // TODO: this also doesn't exist anymore
        // super.configAllSettings(config);
        setDistancePerPulse(distancePerPulse);
        setReverseDirection(reverseDirection);
        reset();
    }

    public CustomCANCoder(int deviceNum, double distancePerPulse) {
        this(deviceNum, distancePerPulse, DEFAULT_REVERSE_DIRECTION);
    }

    // public void setDistancePerPulse(double pulse, SensorTimeBase collectionPeriod) {
    //     this.distancePerPulse = pulse;
    //     super.configFeedbackCoefficient(pulse, "meters", collectionPeriod);
    // }

    // public void setDistancePerPulse(double pulse) {
    //     setDistancePerPulse(pulse, SensorTimeBase.PerSecond);
    // }

    @Override
    public double getRate() {
        try {
            return getRateSafely();
        } catch (Exception e) {
            e.printStackTrace();
            // LogKitten.ex(e);
            return 0;
        }
    }

    @Override
    public double getRateSafely() throws InvalidSensorException {
        return getVelocity().getValue();

    }

    @Override
    public double getDistance() {
        try {
            return getDistanceSafely();
        } catch (Exception e) {
            e.printStackTrace();
            // LogKitten.ex(e);
            return 0;
        }
    }

    @Override
    public double getDistanceSafely() throws InvalidSensorException {
        return getPosition().getValue();
    }

    @Override
    public boolean getDirection() {
        try {
            return getDirectionSafely();
        } catch (Exception e) {
            e.printStackTrace();
            // LogKitten.ex(e);
            return false;
        }
    }

    @Override
    public boolean getDirectionSafely() throws InvalidSensorException {
        // getPosition() returns status signal, can't use >= on status signal
        return !reverseDirection == (getPosition().getValue() >= 0);
    }

    @Override
    public boolean getStopped() {
        try {
            return getStoppedSafely();
        } catch (Exception e) {
            e.printStackTrace();
            // LogKitten.ex(e);
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
    public void setDistancePerPulse(double distancePerPulse) {
        this.distancePerPulse = distancePerPulse;
    }

    @Override
    public boolean getReverseDirection() {
        return reverseDirection;
    }

    @Override
    public void setReverseDirection(boolean reverseDirection) {
        this.reverseDirection = reverseDirection;
        // TODO: configSensorDirection isn't a thing anymore
        // super.configSensorDirection(!reverseDirection);

    }

    @Override
    public void reset() {
        setPosition(0.0);

    }

}
