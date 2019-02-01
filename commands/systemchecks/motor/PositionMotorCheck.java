package org.usfirst.frc4904.standard.commands.systemchecks.motor;

import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;

public class PositionMotorCheck extends SensorMotorCheck {    
    public PositionMotorCheck(String name, double setPosition, PositionSensorMotor... positionMotors) {
        super(name, setPosition, positionMotors);
    }

    public PositionMotorCheck(String name, PositionSensorMotor... positionMotors) {
        this(name, DEFAULT_POSITION, positionMotors);
    }

    public PositionMotorCheck(double setPosition, PositionSensorMotor... positionMotors) {
        this("PositionMotorCheck", setPosition, positionMotors);
    }

    public PositionMotorCheck(PositionSensorMotor... positionMotors) {
        this("PositionMotorCheck", positionMotors);
    }

}