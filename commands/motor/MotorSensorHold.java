package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.sensormotor.PositionSensorMotor;

/**
 * Sets a motor to a position and keeps it there
 * using an encoder.
 *
 */
public class MotorSensorHold extends MotorSet {
	protected PositionSensorMotor motor;
	
	/**
	 * Constructor.
	 * The MotorSensorHold command holds a motor
	 * to a position.
	 * 
	 * @param motor
	 *        A Motor that also implements PositionSensorMotor
	 */
	public <A extends Motor & PositionSensorMotor> MotorSensorHold(A motor) {
		super(motor);
		this.motor = motor;
	}
	
	/**
	 * This will attempt to return a constructed MotorSensorHold
	 * from a motor you are not sure of.
	 * Please make sure you check null after using this
	 * function. There will not be an error if the motor
	 * is not a PositionSensorMotor.
	 * 
	 * @param motor
	 * @return
	 */
	public static <A extends Motor & PositionSensorMotor> MotorSensorHold tryCastMotor(Motor motor) {
		if (motor instanceof PositionSensorMotor) {
			return new MotorSensorHold((A) motor);
		}
		return null;
	}
	
	/**
	 * Sets the motor to this position.
	 * 
	 * @param position
	 */
	public void setPosition(double position) {
		motor.getPositionPID().setSetpoint(position);
		super.set(motor.getPositionPID().get());
	}
}
