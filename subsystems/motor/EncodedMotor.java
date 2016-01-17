package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import edu.wpi.first.wpilibj.SpeedController;

public class EncodedMotor extends Motor {
	protected CustomEncoder encoder;
	protected double P;
	protected double I;
	protected double D;
	protected double maximum;
	protected double minimum;
	
	public EncodedMotor(String name, SpeedController motor, CustomEncoder encoder, double P, double I, double D, double maximum, double minimum, double distancePerPulse, boolean inverted) {
		super(name, motor, inverted);
		this.encoder = encoder;
		this.P = P;
		this.I = I;
		this.D = D;
		this.maximum = maximum;
		this.minimum = minimum;
		encoder.setDistancePerPulse(distancePerPulse);
	}
	
	public EncodedMotor(String name, SpeedController motor, CustomEncoder encoder, double P, double I, double D, double maximum, double minimum, double distancePerPulse) {
		this(name, motor, encoder, P, I, D, maximum, minimum, distancePerPulse, false);
	}
	
	public CustomEncoder getEncoder() {
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
	
	public double getMinimum() {
		return minimum;
	}
	
	public double getMaximum() {
		return maximum;
	}
	
	public void setDistancePerPulse(double distancePerPulse) {
		encoder.setDistancePerPulse(distancePerPulse);
	}
}
