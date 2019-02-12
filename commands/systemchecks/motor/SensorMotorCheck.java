package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;

public abstract class SensorMotorCheck extends MotorCheck {
    protected static final double DEFAULT_POSITION = 50;
    protected static final double POSITION_THRESHOLD = 2.0; // TODO: TEST THIS
	protected static final double VELOCITY_THRESHOLD = 2.0; // TODO: TEST THIS
    protected SensorMotor[] motors;	
    protected double position;

    public SensorMotorCheck(String name, double timeout, double speed, double position, SensorMotor... motors) {
        super(name, timeout, motors);
        this.motors = motors;
        this.speed = speed;
        this.position = position;
    }

    public SensorMotorCheck(String name, double speed, double position, SensorMotor... motors) {
        this(name, DEFAULT_TIMEOUT, speed, position, motors);
    }

    public SensorMotorCheck(double timeout, double speed, double position, SensorMotor... motors) {
        this("SensorMotorCheck", timeout, speed, position, motors);
    }

    public SensorMotorCheck(double speed, double position, SensorMotor... motors) {
        this(DEFAULT_TIMEOUT, speed, position, motors);
    }

    public SensorMotorCheck(String name, double position, SensorMotor... motors) {
        this(name, DEFAULT_TIMEOUT, DEFAULT_SPEED, position, motors);
    }

    public SensorMotorCheck(double position, SensorMotor... motors) {
        this("SensorMotorCheck", position, motors);
    }

    public SensorMotorCheck(String name, SensorMotor... motors) {
        this(name, DEFAULT_POSITION, motors);
    }

    public SensorMotorCheck(SensorMotor... motors) {
        this("SensorMotorCheck", motors);
    }

    @Override
    public void initialize() {
        for (SensorMotor motor : motors) {
            if (motor instanceof VelocitySensorMotor) {
                ((VelocitySensorMotor) motor).set(speed);
            }
        }
    }

    @Override
    public void execute() {
        for (SensorMotor motor : motors) {
            double input;
            try {
                input = motor.getMotionController().getInputSafely();
            }
            catch (InvalidSensorException e) {
                input = 0;
                updateStatusFail(motor.getName(), e);
            }
            if (motor instanceof VelocitySensorMotor) {
                ((VelocitySensorMotor) motor).set(speed);
                if (input - speed > VELOCITY_THRESHOLD) {
                    updateStatusFail(motor.getName(), new Exception("SET SPEED NOT WITHIN REQUIRED THRESHOLD"));
                }
            } else if (motor instanceof PositionSensorMotor) {
                ((PositionSensorMotor) motor).setPosition(position);
                if (input - position > POSITION_THRESHOLD) {
                    updateStatusFail(motor.getName(), new Exception("POSITION NOT WITHIN THRESHOLD"));
                }
            }
        }
    }
}