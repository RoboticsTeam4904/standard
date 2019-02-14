package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;

/**
 * Systemcheck of SensorMotor
 */
public abstract class SensorMotorCheck extends MotorCheck {
    protected static final double DEFAULT_POSITION = 50;
    protected static final double POSITION_THRESHOLD = 2.0; // TODO: TEST THIS
    protected static final double VELOCITY_THRESHOLD = 2.0; // TODO: TEST THIS
    protected final double timeout;
    protected SensorMotor[] motors;
    protected double position;

    /**
     * Systemcheck of SensorMotor
     * 
     * @param name
     *                 Name of check
     * @param timeout
     *                 Duration of check
     * @param speed
     *                 Speed to set VelocitySensorMotors
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 SensorMotors to test
     */
    public SensorMotorCheck(String name, double timeout, double speed, double position, SensorMotor... motors) {
        super(name, timeout, motors);
        this.motors = motors;
        this.timeout = timeout;
        this.speed = speed;
        this.position = position;
    }

    /**
     * Systemcheck of SensorMotor
     * 
     * @param name
     *                 Name of check
     * @param speed
     *                 Speed to set VelocitySensorMotors
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 SensorMotors to test
     */
    public SensorMotorCheck(String name, double speed, double position, SensorMotor... motors) {
        this(name, DEFAULT_TIMEOUT, speed, position, motors);
    }

    /**
     * Systemcheck of SensorMotor
     * 
     * @param timeout
     *                 Duration of check
     * @param speed
     *                 Speed to set VelocitySensorMotors
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 SensorMotors to test
     */
    public SensorMotorCheck(double timeout, double speed, double position, SensorMotor... motors) {
        this("SensorMotorCheck", timeout, speed, position, motors);
    }

    /**
     * Systemcheck of SensorMotor
     * 
     * @param speed
     *                 Speed to set VelocitySensorMotors
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 SensorMotors to test
     */
    public SensorMotorCheck(double speed, double position, SensorMotor... motors) {
        this(DEFAULT_TIMEOUT, speed, position, motors);
    }

    /**
     * Systemcheck of SensorMotor
     * 
     * @param name
     *                 Name of check
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 SensorMotors to test
     */
    public SensorMotorCheck(String name, double position, SensorMotor... motors) {
        this(name, DEFAULT_TIMEOUT, DEFAULT_SPEED, position, motors);
    }

    /**
     * Systemcheck of SensorMotor
     * 
     * @param position
     *                 Position to set PositionSensorMotors
     * @param motors
     *                 SensorMotors to test
     */
    public SensorMotorCheck(double position, SensorMotor... motors) {
        this("SensorMotorCheck", position, motors);
    }

    /**
     * Systemcheck of SensorMotor
     * 
     * @param name
     *               Name of check
     * @param motors
     *               SensorMotors to test
     */
    public SensorMotorCheck(String name, SensorMotor... motors) {
        this(name, DEFAULT_POSITION, motors);
    }

    /**
     * Systemcheck of SensorMotor
     * 
     * @param motors
     *               SensorMotors to test
     */
    public SensorMotorCheck(SensorMotor... motors) {
        this("SensorMotorCheck", motors);
    }

    /**
     * Sets SensorMotors depending on type
     */
    @Override
    public void initialize() {
        for (SensorMotor motor : motors) {
            if (motor instanceof VelocitySensorMotor) {
                ((VelocitySensorMotor) motor).set(speed);
            } else if (motor instanceof PositionSensorMotor) {
                ((PositionSensorMotor) motor).setPosition(position);
            }
        }
    }

    /**
     * Checks precision of sensor motor
     * Checks voltage input and current output of motors
     */
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
                VICheck(motor);
                if (((VelocitySensorMotor) motor).getMotionController().onTarget() && input - speed > VELOCITY_THRESHOLD) {
                    updateStatusFail(motor.getName(), new Exception("SET SPEED NOT WITHIN REQUIRED THRESHOLD"));
                }
            } else if (motor instanceof PositionSensorMotor) {
                if (!motor.getMotionController().onTarget()) {
                    VICheck(motor);
                }
                if (((PositionSensorMotor) motor).getMotionController().onTarget() && input - position > POSITION_THRESHOLD) {
                    updateStatusFail(motor.getName(), new Exception("POSITION NOT WITHIN THRESHOLD"));
                }
            }
        }
    }

    /**
     * Checks if motors are onTarget by end of command
     */
    @Override
    public void end() {
        super.end();
        for (SensorMotor motor : motors) {
            if (!motor.getMotionController().onTarget()) {
                updateStatusFail(motor.getName(),
                    new Exception(String.format("MOTIONCONTROLLER NOT ON TARGET IN %f", timeout)));
            }
        }
    }
}