package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;

/**
 * Systemcheck of VelocitySensorMotors
 */
public class VelocityMotorCheck extends SensorMotorCheck {
    /**
     * Systemcheck of VelocitySensorMotors
     * 
     * @param name
     *                Name of check
     * @param timeout
     *                Duration of check
     * @param speed
     *                Speed to set VelocitySensorMotors
     * @param motors
     *                VelocitySensorMotors to test
     */
    public VelocityMotorCheck(String name, double timeout, double speed, VelocitySensorMotor... motors) {
        super(name, timeout, speed, 0, motors);
    }

    /**
     * Systemcheck of VelocitySensorMotors
     * 
     * @param name
     *                Name of check
     * @param speed
     *                Speed to set VelocitySensorMotors
     * @param motors
     *                VelocitySensorMotors to test
     */
    public VelocityMotorCheck(String name, double speed, VelocitySensorMotor... motors) {
        super(name, speed, 0, motors);
    }

    /**
     * Systemcheck of VelocitySensorMotors
     * 
     * @param name
     *                Name of check
     * @param motors
     *                VelocitySensorMotors to test
     */
    public VelocityMotorCheck(String name, VelocitySensorMotor... motors) {
        this(name, DEFAULT_SPEED, motors);
    }

    /**
     * Systemcheck of VelocitySensorMotors
     * 
     * @param speed
     *                Speed to set VelocitySensorMotors
     * @param motors
     *                VelocitySensorMotors to test
     */
    public VelocityMotorCheck(double speed, VelocitySensorMotor... motors) {
        this("VelocityMotorCheck", speed, motors);
    }

    /**
     * Systemcheck of VelocitySensorMotors
     * 
     * @param motors
     *                VelocitySensorMotors to test
     */
    public VelocityMotorCheck(VelocitySensorMotor... motors) {
        this("VelocityMotorCheck", motors);
    }
}