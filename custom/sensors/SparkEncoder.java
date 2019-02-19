package org.usfirst.frc4904.standard.custom.sensors;


import com.revrobotics.CANEncoder;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.motioncontrollers.Spark;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Built-in encoder on CANSpark NEO
 * Implements generic encoder interface
 */
public class SparkEncoder implements CustomEncoder {
    protected final double SECONDS_PER_MINUTE = 60.0;
    protected final CANEncoder encoder;
    protected PIDSourceType pidSource;
    protected double distancePerRotation;
    protected boolean reverseDirection;
    protected double truePosition;
    protected double trueVelocity;

    /**
     * Built-in encoder on CANSpark NEO
     * Implements generic encoder interface
     * 
     * @param spark
     *                            Spark NEO motor used to construct encoder
     * @param reverseDirection
     *                            True to reverse direction of encoder
     * @param distancePerRotation
     *                            Conversion factor from encoder readings to distance/velocity readings
     *                            Should be in units of meters per second
     * @param pidSource
     *                            kDisplacement for position readings, kRate for velocity readings
     */
    public SparkEncoder(Spark spark, boolean reverseDirection, double distancePerRotation, PIDSourceType pidSource) {
        encoder = new CANEncoder(spark);
        this.reverseDirection = reverseDirection;
        this.distancePerRotation = distancePerRotation;
        setPIDSourceType(pidSource);
    }

    /**
     * Built-in encoder on CANSpark NEO
     * Implements generic encoder interface
     * 
     * @param spark
     *                            Spark NEO motor used to construct encoder
     * @param reverseDirection
     *                            True to reverse direction of encoder
     * @param distancePerRotation
     *                            Conversion factor from encoder readings to distance/velocity readings
     *                            Should be in units of meters per second
     */
    public SparkEncoder(Spark spark, boolean reverseDirection, double distancePerRotation) {
        this(spark, reverseDirection, distancePerRotation, PIDSourceType.kDisplacement);
    }

    /**
     * Built-in encoder on CANSpark NEO
     * Implements generic encoder interface
     * 
     * @param spark
     *                            Spark NEO motor used to construct encoder
     * @param distancePerRotation
     *                            Conversion factor from encoder readings to distance/velocity readings
     *                            Should be in units of meters per second
     */
    public SparkEncoder(Spark spark, double distancePerRotation) {
        this(spark, false, distancePerRotation);
    }

    /**
     * Built-in encoder on CANSpark NEO
     * Implements generic encoder interface
     * DistancePerRotation set to 1 to return raw encoder readings
     * 
     * @param spark
     *              Spark NEO motor used to construct encoder
     */
    public SparkEncoder(Spark spark) {
        this(spark, false, 1.0);
    }

    /**
     * Set encoder type
     * 
     * @param pidSource
     *                  kDisplacement for position readings, kRate for velocity readings
     */
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        this.pidSource = pidSource;
    }

    /**
     * @return pidSource
     *         returns type of encoder
     */
    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSource;
    }

    /**
     * Returns encoder reading depending on pidSource
     */
    @Override
    public double pidGet() {
        try {
            return pidGetSafely();
        }
        catch (InvalidSensorException e) {
            LogKitten.e(e);
            return 0.0;
        }
    }

    /**
     * Gets encoder position reading
     */
    @Override
    public int get() {
        try {
            return getSafely();
        }
        catch (InvalidSensorException e) {
            LogKitten.e(e);
            return 0;
        }
    }

    /**
     * Returns rate in meters per second (given that distancePerRotation is in meters per rotation)
     */
    @Override
    public double getDistance() {
        try {
            return getDistanceSafely();
        }
        catch (InvalidSensorException e) {
            LogKitten.e(e);
            return 0.0;
        }
    }

    @Override
    public boolean getDirection() {
        try {
            return getDirectionSafely();
        }
        catch (InvalidSensorException e) {
            LogKitten.e(e);
            return false;
        }
    }

    /**
     * Check if the NEO has stopped moving
     */
    @Override
    public boolean getStopped() {
        try {
            return getStoppedSafely();
        }
        catch (InvalidSensorException e) {
            LogKitten.e(e);
            return false;
        }
    }

    /**
     * Returns distance in meters (given that distancePerRotation is in meters per rotation)
     */
    @Override
    public double getRate() {
        try {
            return getRateSafely();
        }
        catch (InvalidSensorException e) {
            LogKitten.e(e);
            return 0.0;
        }
    }

    @Override
    public double getDistancePerPulse() {
        return distancePerRotation;
    }

    @Override
    public void setDistancePerPulse(double distancePerRotation) {
        this.distancePerRotation = distancePerRotation;
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
        truePosition = encoder.getPosition();
        trueVelocity = encoder.getVelocity();
    }

    @Override
    public double pidGetSafely() throws InvalidSensorException {
        switch (pidSource) {
            case kDisplacement:
                return getDistance();
            case kRate:
                return getRate();
            default:
                return getDistance();
        }
    }

    @Override
    public int getSafely() throws InvalidSensorException {
        return (int) encoder.getPosition();
    }

    /**
     * Returns distance in meters (given that distancePerRotation is in meters per rotation)
     */
    @Override
    public double getDistanceSafely() throws InvalidSensorException {
        if (reverseDirection) {
            return distancePerRotation * (encoder.getPosition() - truePosition) * -1.0;
        } else {
            return distancePerRotation * (encoder.getPosition() - truePosition);
        }
    }

    @Override
    public boolean getDirectionSafely() throws InvalidSensorException {
        return !reverseDirection == (encoder.getVelocity() >= 0);
    }

    @Override
    public boolean getStoppedSafely() throws InvalidSensorException {
        return Util.isZero(getRate());
    }

    /**
     * Returns rate in meters per second (given that distancePerRotation is in meters per rotation)
     */
    @Override
    public double getRateSafely() throws InvalidSensorException {
        if (reverseDirection) {
            return (encoder.getVelocity() - trueVelocity) * distancePerRotation / SECONDS_PER_MINUTE * -1.0;
        } else {
            return (encoder.getVelocity() - trueVelocity) * distancePerRotation / SECONDS_PER_MINUTE;
        }
    }
}