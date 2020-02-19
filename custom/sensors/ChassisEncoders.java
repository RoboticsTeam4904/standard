package org.usfirst.frc4904.standard.custom.sensors;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Encoders for the chassis that can calculate netDisplacement
 */
public class ChassisEncoders extends EncoderPair implements Subsystem {

    protected double robotWidth;
    protected double netDisplacement;
    protected double netDisplacementAngle; // in radians
    protected double lastLeftEncoderDistance;
    protected double lastRightEncoderDistance;

    public ChassisEncoders(CustomEncoder leftEncoder, CustomEncoder rightEncoder, double distanceTolerance,
            double rateTolerance, double robotWidth) {
        super(leftEncoder, rightEncoder, distanceTolerance, rateTolerance);
        CommandScheduler.getInstance().registerSubsystem(this);
        setRobotWidth(robotWidth);
        reset();
    }

    public ChassisEncoders(CustomEncoder leftEncoder, CustomEncoder rightEncoder, double robotWidth) {
        this(leftEncoder, rightEncoder, DEFAULT_DISTANCE_TOLERANCE, DEFAULT_RATE_TOLERANCE, robotWidth);
    }

    @Override
    public void periodic() {
        updateDisplacement();
    }

    public void setRobotWidth(double robotWidth) {
        this.robotWidth = robotWidth;
    }

    public double getleftEncoderDistance() {
        return encoders[0].getDistance() - offset[0];
    }

    public double getRightEncoderDistance() {
        return (encoders[1].getDistance() - offset[1]);
    }

    public void updateDisplacement() {
        double leftEncoderDifference = getleftEncoderDistance() - lastLeftEncoderDistance;
        double rightEncoderDifference = getRightEncoderDistance() - lastRightEncoderDistance;
        double deltaAngle = (rightEncoderDifference - leftEncoderDifference) / robotWidth;
        netDisplacementAngle += deltaAngle;
        netDisplacement += 2 * Math.sin(deltaAngle / 2) * (leftEncoderDifference / deltaAngle + robotWidth / 2);
        lastLeftEncoderDistance = getleftEncoderDistance();
        lastRightEncoderDistance = getRightEncoderDistance();
    }

    @Override
    public void reset() {
        super.reset();
        netDisplacement = 0.0;
        netDisplacementAngle = 0.0;
        lastLeftEncoderDistance = 0.0;
        lastRightEncoderDistance = 0.0;
    }

    public double getNetDisplacement() {
        return netDisplacement;
    }

    public double getNetDisplacementAngle() {
        return netDisplacementAngle;
    }
}