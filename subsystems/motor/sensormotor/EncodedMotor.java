package org.usfirst.frc4904.standard.subsystems.motor.sensormotor;


import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An encoded motor is a motor with a set
 * of variables relevant to controlling
 * a motor with an encoder. It contains
 * an Encoder, PID constants, and range
 * information.
 *
 */
public class EncodedMotor extends Motor implements PositionSensorMotor, SpeedSensorMotor {
	protected CustomEncoder encoder;
	protected PIDController ratePID;
	protected PIDController positionPID;
	
	public EncodedMotor(String name, boolean inverted, CustomEncoder encoder, PIDController ratePID, PIDController positionPID, SpeedController... motor) {
		super(name, inverted, motor);
		this.encoder = encoder;
		this.ratePID = ratePID;
		this.positionPID = positionPID;
	}
	
	/**
	 * Returns the rate of rotation
	 * of the motor.
	 */
	public double getRate() {
		return encoder.getRate();
	}
	
	/**
	 * Returns position of the motor
	 */
	public double getPosition() {
		return encoder.getDistance();
	}
	
	/**
	 * Returns the encoder
	 * 
	 * @return
	 */
	public CustomEncoder getEncoder() {
		return encoder;
	}
	
	/**
	 * Returns rate PIDController
	 */
	public PIDController getRatePID() {
		return ratePID;
	}
	
	/**
	 * Returns position PIDController
	 */
	public PIDController getPositionPID() {
		return positionPID;
	}
}
