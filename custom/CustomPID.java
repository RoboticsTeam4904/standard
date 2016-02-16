package org.usfirst.frc4904.standard.custom;


import edu.wpi.first.wpilibj.PIDSource;

public class CustomPID {
	protected double P;
	protected double I;
	protected double D;
	protected double F;
	protected final PIDSource source;
	protected double setpoint;
	protected double totalError;
	protected double lastError;
	protected double lastUpdate;
	protected boolean enable;
	protected double absoluteTolerance;
	
	public CustomPID(double P, double I, double D, double F, PIDSource source) {
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
		this.source = source;
		setpoint = source.pidGet();
		enable = true;
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
	
	public void setAbsoluteTolerance(double absoluteTolerance) {
		if (absoluteTolerance >= 0) {
			this.absoluteTolerance = absoluteTolerance;
		}
		// ABSOLUTE TOLERANCE IS NEGATIVE. THAT'S NOT GOOD.
	}
	
	public void reset() {
		setpoint = source.pidGet();
		totalError = 0;
		lastError = 0;
	}
	
	public void enable() {
		enable = true;
	}
	
	public void disable() {
		enable = false;
	}
	
	public double getError() {
		return lastError;
	}
	
	public double getSetpoint() {
		return setpoint;
	}
	
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}
	
	public double get() {
		if (!enable) {
			return F * setpoint;
		}
		double input = source.pidGet();
		double deltaT = (System.currentTimeMillis() - lastUpdate) / 1000.0;
		double error = setpoint - input;
		totalError += error * deltaT;
		double result = P * error + I * totalError + D * ((error - lastError) / deltaT) + F * setpoint;
		lastError = error;
		return result;
	}
	
	public boolean onTarget() {
		return Math.abs(getError()) <= absoluteTolerance;
	}
}
