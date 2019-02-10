package org.usfirst.frc4904.standard.custom.sensors;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SparkEncoder implements CustomEncoder {
    protected final CANEncoder encoder;
    protected PIDSourceType pidSource;
    protected double distancePerPulse;
    protected boolean reverseDirection;
    protected double truePosition;
    protected double trueVelocity;

    public SparkEncoder(CANSparkMax spark, boolean reverseDirection, double distancePerPulse) {
        encoder = new CANEncoder(spark);
        this.reverseDirection = reverseDirection;
        this.distancePerPulse = distancePerPulse;
        setPIDSourceType(PIDSourceType.kDisplacement);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        this.pidSource = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSource;
    }

    @Override
    public double pidGet() {
        if (pidSource == PIDSourceType.kDisplacement) {
            return getDistance();
        } else {
            return getRate();
        }
    }

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
        if (pidSource == PIDSourceType.kDisplacement) {
            truePosition = encoder.getPosition();
        } else {
            trueVelocity = encoder.getVelocity();
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