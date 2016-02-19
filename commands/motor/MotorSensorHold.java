package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;

/**
 * Sets a motor to a position and keeps it there using an encoder.
 */
public class MotorSensorHold extends MotorSet {
	protected SensorMotor motor;
	
	/**
	 * Constructor.
	 * The MotorSensorHold command holds a motor to a position.
	 *
	 * @param motor
	 *        A Motor that also implements PositionSensorMotor.
	 */
	public MotorSensorHold(SensorMotor motor) {
		super(motor);
		this.motor = motor;
	}
	
	/**
	 * Sets the motor to this position.
	 *
	 * @param position
	 *        The position to set the motor to.
	 */
	public void setPosition(double position) {
		motor.setPosition(position);
	}
}
