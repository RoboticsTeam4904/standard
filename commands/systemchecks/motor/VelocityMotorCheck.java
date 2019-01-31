package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;

public class VelocityMotorCheck extends MotorCheck {
    protected final VelocitySensorMotor[] velocityMotors;
    protected static final double VELOCITY_THRESHOLD = 0.01; // TODO: Change this

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

    @Override
    public void initialize() {
        for (VelocitySensorMotor motor : velocityMotors) {
            motor.set(speed);
        }
    }

    @Override
    public void execute() {
        for (VelocitySensorMotor motor : velocityMotors) {
            try {
                motor.set(speed);
                if (Math.abs(motor.getMotionController().getSensor().pidGetSafely() - speed) > VELOCITY_THRESHOLD) {
                    updateStatus(motor.getName(), SystemStatus.FAIL, new Exception("SET SPEED NOT WITHIN REQUIRED THRESHOLD"));
                }
            }
            catch (InvalidSensorException e) {
                updateStatus(motor.getName(), SystemStatus.FAIL, e);
            }
        }
    }
}