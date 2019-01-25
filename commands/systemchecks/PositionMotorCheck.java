package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;

public class PositionMotorCheck extends SubsystemCheck {
    protected final PositionSensorMotor[] positionMotors;
    protected final double setPosition;
    
    public PositionMotorCheck(String name, double setPosition, PositionSensorMotor... positionMotors) {
        super(name, positionMotors);
        this.positionMotors = positionMotors;
        this.setPosition = setPosition;
    }

    public PositionMotorCheck(String name, PositionSensorMotor... positionMotors) {
        this(name, 50, positionMotors);
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
            } catch (Exception e) {
                updateStatus(motor.getName(), SystemStatus.FAIL, e.getMessage());
            }
        }
    }
}