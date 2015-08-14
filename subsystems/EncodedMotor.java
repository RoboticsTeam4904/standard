package org.usfirst.frc4904.cmdbased.subsystems;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class EncodedMotor extends PIDSubsystem {
	protected final SpeedController motor;
	protected final Encoder encoder;
	
	public EncodedMotor(String name, double P, double I, double D, SpeedController motor, Encoder encoder) {
		super(name, P, I, D);
		this.motor = motor;
		this.encoder = encoder;
		getPIDController().setOutputRange(-1, 1);
		getPIDController().setContinuous(false);
	}
	
	protected void initDefaultCommand() {}
	
	protected double returnPIDInput() {
		return encoder.getRate();
	}
	
	protected void usePIDOutput(double speed) {
		motor.set(speed);
	}
	
	public void set(double speed) {
		setSetpoint(speed);
	}
}