package org.usfirst.frc4904.standard.custom;


import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.util.BoundaryException;

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
	protected boolean continuous;
	protected double inputMax;
	protected double inputMin;
	protected boolean capOutput;
	protected double outputMax;
	protected double outputMin;
	
	public CustomPID(double P, double I, double D, double F, PIDSource source) {
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
		this.source = source;
		enable = true;
		absoluteTolerance = 0.0001; // Nonzero to avoid floating point errors
		capOutput = false;
		continuous = false;
		inputMin = 0.0;
		inputMax = 0.0;
		outputMin = 0.0;
		outputMax = 0.0;
		this.reset();
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
		throw new BoundaryException("Absolute tolerance negative");
	}
	
	public void setInputRange(double minimum, double maximum) {
		if (minimum > maximum) {
			throw new BoundaryException("Minimum is greater than maximum");
		}
		inputMin = minimum;
		inputMax = maximum;
	}
	
	public void setOutputRange(double minimum, double maximum) {
		outputMin = minimum;
		outputMax = maximum;
		capOutput = true;
	}
	
	public void disableOutputRange() {
		capOutput = false;
	}
	
	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
	}
	
	public void reset() {
		setpoint = source.pidGet();
		totalError = 0;
		lastError = 0;
		lastUpdate = System.currentTimeMillis();
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
		if (capOutput) {
			if (result > outputMax) {
				return outputMax;
			} else if (result < outputMin) {
				return outputMin;
			}
		}
		lastUpdate = System.currentTimeMillis();
		return result;
	}
	
	public boolean onTarget() {
		return Math.abs(getError()) <= absoluteTolerance;
	}
}
