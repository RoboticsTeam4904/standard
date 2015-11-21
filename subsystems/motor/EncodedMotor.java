package org.usfirst.frc4904.standard.subsystems.motor;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class EncodedMotor extends Motor {
	protected Encoder encoder;
	protected double P;
	protected double I;
	protected double D;
	
	public EncodedMotor(String name, SpeedController motor, Encoder encoder, double P, double I, double D, boolean inverted) {
		super(name, motor, inverted);
		this.encoder = encoder;
		this.P = P;
		this.I = I;
		this.D = D;
	}
	
	public EncodedMotor(String name, SpeedController motor, Encoder encoder, double P, double I, double D) {
		this(name, motor, encoder, P, I, D, false);
	}
	
	public Encoder getEncoder() {
		return encoder;
	}
	
	public double getP() {
		return P;
	}
	
	public double getI() {
		return I;
	}
	
	public double getD() {
		return D;
	}
}
