package org.usfirst.frc4904.standard.custom.motioncontrollers;


import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.util.BoundaryException;

public abstract class MotionController {
	protected final PIDSource source;
	protected double setpoint;
	protected double absoluteTolerance;
	protected boolean continuous;
	protected double inputMax;
	protected double inputMin;
	protected boolean capOutput;
	protected double outputMax;
	protected double outputMin;
	protected boolean enable;
	
	public MotionController(PIDSource source) {
		this.source = source;
		enable = true;
		absoluteTolerance = 0.0001; // Nonzero to avoid floating point errors
		capOutput = false;
		continuous = false;
		inputMin = 0.0;
		inputMax = 0.0;
		outputMin = 0.0;
		outputMax = 0.0;
		reset();
	}
	
	public abstract void reset();
	
	public abstract double get();
	
	public abstract double getError();
	
	public double getSetpoint() {
		return setpoint;
	}
	
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
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
	
	public void enable() {
		enable = true;
	}
	
	public void disable() {
		enable = false;
	}
	
	public boolean onTarget() {
		return Math.abs(getError()) <= absoluteTolerance;
	}
}
