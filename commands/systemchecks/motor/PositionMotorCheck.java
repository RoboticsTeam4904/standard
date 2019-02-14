package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;

/**
 * Systemcheck of PositionSensorMotors
 */
public class PositionMotorCheck extends SensorMotorCheck {
    /**
     * Systemcheck of PositionSensorMotors
     * 
     * @param name
     *                 Name of check
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 PositionSensorMotors to test
     */
    public PositionMotorCheck(String name, double position, PositionSensorMotor... motors) {
        super(name, position, motors);
    }

    /**
     * Systemcheck of PositionSensorMotors
     * 
     * @param name
     *               Name of check
     * @param motors
     *               PositionSensorMotors to test
     */
    public PositionMotorCheck(String name, PositionSensorMotor... motors) {
        this(name, DEFAULT_POSITION, motors);
    }

    /**
     * Systemcheck of PositionSensorMotors
     * 
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 PositionSensorMotors to test
     */
    public PositionMotorCheck(double position, PositionSensorMotor... motors) {
        this("PositionMotorCheck", position, motors);
    }

    /**
     * Systemcheck of PositionSensorMotors
     * 
     * @param motors
     *               PositionSensorMotors to test
     */
    public PositionMotorCheck(PositionSensorMotor... motors) {
        this("PositionMotorCheck", motors);
    }
}