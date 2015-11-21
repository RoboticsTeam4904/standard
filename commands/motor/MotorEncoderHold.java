package org.usfirst.frc4904.standard.commands.motor;


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
	
	public MotorEncoderHold(Motor motor, Encoder encoder, double P, double I, double D, double minimum, double maximum) {
		super(motor);
		this.encoder = encoder;
		pid = new PIDController(P, I, D, encoder, this);
		pid.setInputRange(minimum, maximum);
		pid.setOutputRange(-1.0, 1.0);
	}
	
	public void setPosition(double position) {
		pid.setSetpoint(position);
	}
	
	public void pidWrite(double output) {
		super.set(output);
	}
}
