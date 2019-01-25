package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;

public class VelocityMotorCheck extends MotorCheck {
    protected final VelocitySensorMotor[] velocityMotors;

    public VelocityMotorCheck(String name, double speed, VelocitySensorMotor... velocityMotors) {
        super(name, speed, velocityMotors);
        this.velocityMotors = velocityMotors;
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

    public void initialize() {
        for (VelocitySensorMotor motor: velocityMotors){
            motor.set(speed);
        }
    }

    public void execute() {
        for (VelocitySensorMotor motor: velocityMotors){
            try {
                motor.set(speed);
            } catch (Exception e) {
                updateStatus(motor.getName(), StatusMessage.SystemStatus.FAIL, e.getMessage());
            }
        }
    }
}