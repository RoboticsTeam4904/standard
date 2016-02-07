package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.sensormotor.SpeedSensorMotor;

/**
 * This class handles speed based PID in a similar way to motor set.
 */
public class MotorSensorSet extends MotorSet {
	protected final SpeedSensorMotor motor;
	
	/**
	 * Constructor.
	 * The MotorSensorSet command keeps a
	 * motor set to a specific speed.
	 * 
	 * @param motor
	 *        A motor that also implements SpeedSensorMotor
	 */
	public <A extends Motor & SpeedSensorMotor> MotorSensorSet(A motor) {
		super(motor); // Calls requires (so we don't need to recall)
		this.motor = motor;
	}
	
	/**
	 * This will attempt to return a constructed MotorSensorSet
	 * from a motor you are not sure of.
	 * Please make sure you check null after using this
	 * function. There will not be an error if the motor
	 * is not a SpeedSensorMotor.
	 * 
	 * @param motor
	 * @return
	 */
	public static <A extends Motor & SpeedSensorMotor> MotorSensorSet tryCastMotor(Motor motor) {
		if (motor instanceof SpeedSensorMotor) {
			return new MotorSensorSet((A) motor);
		}
		return null;
	}
	
	/**
	 * Sets the motor to this speed
	 * 
	 * @param
	 * 		speed
	 *        (a double representing
	 *        the desired speed)
	 */
	public void set(double speed) {
		motor.getRatePID().setSetpoint(speed);
		super.set(motor.getRatePID().get());
	}
}
