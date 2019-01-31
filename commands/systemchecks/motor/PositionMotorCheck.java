package org.usfirst.frc4904.standard.commands.systemchecks.motor;

import org.usfirst.frc4904.standard.commands.systemchecks.SubsystemCheck;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;

public class PositionMotorCheck extends SubsystemCheck {
    protected final PositionSensorMotor[] positionMotors;
    protected final double setPosition;
    protected static final double DEFAULT_POSITION = 50;
    protected static final double POSITION_THRESHOLD = 0.01;
    
    public PositionMotorCheck(String name, double setPosition, PositionSensorMotor... positionMotors) {
        super(name, positionMotors);
        this.positionMotors = positionMotors;
        this.setPosition = setPosition;
    }

    public PositionMotorCheck(String name, PositionSensorMotor... positionMotors) {
        this(name, DEFAULT_POSITION, positionMotors);
    }

    public PositionMotorCheck(double setPosition, PositionSensorMotor... positionMotors) {
        this("PositionSensorMotorCheck", setPosition, positionMotors);
    }

    public PositionMotorCheck(PositionSensorMotor... positionMotors) {
        this("PositionSensorMotorCheck", positionMotors);
    }

    @Override
    public void initialize() {
        for (PositionSensorMotor motor : positionMotors) {
            try {
                motor.setPosition(setPosition);
                if (Math.abs(motor.getMotionController().getSensor().pidGetSafely() - setPosition) > POSITION_THRESHOLD) {
                    updateStatus(motor.getName(), SystemStatus.FAIL, new Exception("POSITION NOT WITHIN THRESHOLD"));
                }
            } catch (Exception e) {
                updateStatus(motor.getName(), SystemStatus.FAIL, e);
            }
        }
    }
}