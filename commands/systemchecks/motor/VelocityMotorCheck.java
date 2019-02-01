package org.usfirst.frc4904.standard.commands.systemchecks.motor;



import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;

public class VelocityMotorCheck extends SensorMotorCheck {

    public VelocityMotorCheck(String name, double timeout, double speed, VelocitySensorMotor... velocityMotors) {
        super(name, timeout, speed, 0, velocityMotors);
    }

    public VelocityMotorCheck(String name, double speed, VelocitySensorMotor... velocityMotors) {
        super(name, speed, 0, velocityMotors);
    }

    public VelocityMotorCheck(String name, VelocitySensorMotor... velocityMotors) {
        this(name, DEFAULT_SPEED, velocityMotors);
    }

    public VelocityMotorCheck(double speed, VelocitySensorMotor... velocityMotors) {
        this("VelocityMotorCheck", speed, velocityMotors);
    }

    public VelocityMotorCheck(VelocitySensorMotor... velocityMotors) {
        this("VelocityMotorCheck", velocityMotors);
    }
}