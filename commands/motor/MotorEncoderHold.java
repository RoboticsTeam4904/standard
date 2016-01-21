package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.subsystems.motor.EncodedMotor;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Sets a motor to a position and keeps it there
 * using an encoder.
 *
 */
public class MotorEncoderHold extends MotorSet implements PIDOutput {
	protected CustomEncoder encoder;
	protected PIDController pid;
	
	/**
	 * Constructor.
	 * The MotorEncoderHold command holds a motor
	 * to a position using an encoder.
	 * 
	 * @param motor
	 * @param encoder
	 * @param P
	 * @param I
	 * @param D
	 * @param minimum
	 * @param maximum
	 */
	public MotorEncoderHold(Motor motor, CustomEncoder encoder, double P, double I, double D, double minimum, double maximum) {
		super(motor);
		this.encoder = encoder;
		pid = new PIDController(P, I, D, encoder, this);
		pid.setInputRange(minimum, maximum);
		pid.setOutputRange(-1.0, 1.0);
		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
	}
	
	/**
	 * Runs this command using the defaults
	 * from the EncodedMotor. (An encoded
	 * motor basically is a container for
	 * all of these variables).
	 * 
	 * @param motor
	 */
	public MotorEncoderHold(EncodedMotor motor) {
		this(motor, motor.getEncoder(), motor.getP(), motor.getI(), motor.getD(), motor.getMinimum(), motor.getMaximum());
	}
	
	/**
	 * Tries to run this command on
	 * any motor using the motor's defaults.
	 * 
	 * @param motor
	 * @return
	 */
	public static MotorEncoderHold tryCastMotor(Motor motor) {
		if (motor instanceof EncodedMotor) {
			return new MotorEncoderHold((EncodedMotor) motor);
		}
		return null;
	}
	
	/**
	 * Sets the motor to this position.
	 * 
	 * @param position
	 */
	public void setPosition(double position) {
		pid.setSetpoint(position);
	}
	
	public void pidWrite(double output) {
		super.set(output);
	}
}
