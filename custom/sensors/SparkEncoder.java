package org.usfirst.frc4904.standard.custom.sensors;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Built-in encoder on CANSpark NEO
 * Implements generic encoder interface
 */
public class SparkEncoder implements CustomEncoder {
    protected final CANEncoder encoder;
    protected PIDSourceType pidSource;
    protected double distancePerPulse;
    protected boolean reverseDirection;
    protected double truePosition;
    protected double trueVelocity;

    /**
     * Built-in encoder on CANSpark NEO
     * Implements generic encoder interface
     * 
     * @param spark
     *                         Spark NEO motor used to construct encoder
     * @param reverseDirection
     *                         True to reverse direction of encoder
     * @param distancePerPulse
     *                         Conversion factor from encoder ticks to distance
     * @param pidSource
     *                         kDisplacement for position readings, kRate for velocity readings
     */
    public SparkEncoder(CANSparkMax spark, boolean reverseDirection, double distancePerPulse, PIDSourceType pidSource) {
        encoder = new CANEncoder(spark);
        this.reverseDirection = reverseDirection;
        this.distancePerPulse = distancePerPulse;
        setPIDSourceType(pidSource);
    }

    /**
     * Built-in encoder on CANSpark NEO
     * Implements generic encoder interface
     * 
     * @param spark
     *                         Spark NEO motor used to construct encoder
     * @param reverseDirection
     *                         True to reverse direction of encoder
     * @param distancePerPulse
     *                         Conversion factor from encoder ticks to distance
     */
    public SparkEncoder(CANSparkMax spark, boolean reverseDirection, double distancePerPulse) {
        this(spark, reverseDirection, distancePerPulse, PIDSourceType.kDisplacement);
    }

    /**
     * Built-in encoder on CANSpark NEO
     * Implements generic encoder interface
     * 
     * @param spark
     *                         Spark NEO motor used to construct encoder
     * @param distancePerPulse
     *                         Conversion factor from encoder ticks to distance
     */
    public SparkEncoder(CANSparkMax spark, double distancePerPulse) {
        this(spark, false, distancePerPulse);
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
        switch (pidSource) {
            case kDisplacement:
                return getDistance();
            case kRate:
                return getRate();
            default:
                return getDistance();
        }
    }

    /**
     * Gets encoder position reading
     */
    @Override
    public int get() {
        return (int) encoder.getPosition();
    }

    @Override
    public double getDistance() {
        if (reverseDirection) {
            return distancePerPulse * (encoder.getPosition() - truePosition) * -1.0;
        } else {
            return distancePerPulse * (encoder.getPosition() - truePosition);
        }
    }

    @Override
    public boolean getDirection() {
        return !reverseDirection == (encoder.getVelocity() >= 0);
    }

    /**
     * Check if the NEO has stopped moving
     */
    @Override
    public boolean getStopped() {
        return Util.isZero(getRate());
    }

    @Override
    public double getRate() {
        if (reverseDirection) {
            return (encoder.getVelocity() - trueVelocity) * distancePerPulse * -1.0;
        } else {
            return (encoder.getVelocity() - trueVelocity) * distancePerPulse;
        }
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
    }

    @Override
    public void reset() {
        switch (pidSource) {
            case kDisplacement:
                truePosition = encoder.getPosition();
                break;
            case kRate:
                trueVelocity = encoder.getVelocity();
                break;
        }
    }

    @Override
    public double pidGetSafely() {
        return pidGet();
    }

    @Override
    public int getSafely() {
        return get();
    }

    @Override
    public double getDistanceSafely() {
        return getDistance();
    }

    @Override
    public boolean getDirectionSafely() {
        return getDirection();
    }

    @Override
    public boolean getStoppedSafely() {
        return getStopped();
    }

    @Override
    public double getRateSafely() {
        return getRate();
    }
}