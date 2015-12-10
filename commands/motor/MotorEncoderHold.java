package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.EncodedMotor;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/*
 * This class holds a motor to a position from an input
 */
public class MotorEncoderHold extends MotorSet implements PIDOutput {
	protected Encoder encoder;
	protected PIDController pid;
	
	/**
	 * 
	 * @param motor
	 * @param encoder
	 * @param P
	 * @param I
	 * @param D
	 * @param minimum
	 * @param maximum
	 */
	public MotorEncoderHold(Motor motor, Encoder encoder, double P, double I, double D, double minimum, double maximum) {
		super(motor);
		this.encoder = encoder;
		pid = new PIDController(P, I, D, encoder, this);
		pid.setInputRange(minimum, maximum);
		pid.setOutputRange(-1.0, 1.0);
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
	}
	
	public MotorEncoderHold(EncodedMotor motor) {
		this(motor, motor.getEncoder(), motor.getP(), motor.getI(), motor.getD(), motor.getMinimum(), motor.getMaximum());
	}
	
	public static MotorEncoderHold tryCastMotor(Motor motor) {
		if (motor instanceof EncodedMotor) {
			return new MotorEncoderHold((EncodedMotor) motor);
		}
		return null;
	}
	
	public void setPosition(double position) {
		pid.setSetpoint(position);
	}
	
	public void pidWrite(double output) {
		super.set(output);
	}
}
