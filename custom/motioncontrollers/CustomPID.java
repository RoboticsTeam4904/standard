package org.usfirst.frc4904.standard.custom.motioncontrollers;


import edu.wpi.first.wpilibj.PIDSource;

public class CustomPID extends MotionController {
	protected double P;
	protected double I;
	protected double D;
	protected double F;
	protected final PIDSource source;
	protected double totalError;
	protected double lastError;
	protected double lastUpdate;
	
	public CustomPID(double P, double I, double D, double F, PIDSource source) {
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
		this.source = source;
	}
	
	public CustomPID(double P, double I, double D, PIDSource source) {
		this(P, I, D, 0.0, source);
	}
	
	public CustomPID(PIDSource source) {
		this(0, 0, 0, source);
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
	
	public double getF() {
		return F;
	}
	
	public void setPID(double P, double I, double D) {
		this.P = P;
		this.I = I;
		this.D = D;
	}
	
	public void setPIDF(double P, double I, double D, double F) {
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
	}
	
	public void reset() {
		setpoint = source.pidGet();
		totalError = 0;
		lastError = 0;
	}
	
	public double getError() {
		return lastError;
	}
	
	public double get() {
		if (!enable) {
			return F * setpoint;
		}
		double input = source.pidGet();
		double deltaT = (System.currentTimeMillis() - lastUpdate) / 1000.0;
		double error = setpoint - input;
		if (continuous) {
			if (Math.abs(error) > (inputMax - inputMin) / 2) {
				if (error > 0) {
					error = error - inputMax + inputMin;
				} else {
					error = error + inputMax - inputMin;
				}
			}
		}
		totalError += error * deltaT;
		double result = P * error + I * totalError + D * ((error - lastError) / deltaT) + F * setpoint;
		lastError = error;
		if (result > outputMax) {
			return outputMax;
		} else if (result < outputMin) {
			return outputMin;
		}
		return result;
	}
}
