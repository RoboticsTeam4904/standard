package org.usfirst.frc4904.standard.custom.motioncontrollers;


import edu.wpi.first.wpilibj.PIDSource;

public class CustomPID extends MotionController {
	protected double P;
	protected double I;
	protected double D;
	protected double F;
	protected double totalError;
	protected double lastError;
	protected double lastUpdate;
	
	public CustomPID(double P, double I, double D, double F, PIDSource source) {
		super(source);
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
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
	
	@Override
	public void reset() {
		setpoint = source.pidGet();
		totalError = 0;
		lastError = 0;
	}
	
	@Override
	public double getError() {
		return lastError;
	}
	
	@Override
	/**
	 * Get the current output of the PID loop.
	 * This should be used to set the output (like a Motor).
	 * 
	 * @return The current output of the PID loop.
	 */
	public double get() {
		// If PID is not enabled, use feedforward only
		if (!enable) {
			return F * setpoint;
		}
		double input = source.pidGet();
		double deltaT = (System.currentTimeMillis() - lastUpdate) / 1000.0;
		double error = setpoint - input;
		// Account for continuous input ranges
		if (continuous) {
			double range = inputMax - inputMin;
			// If the error is more than half of the range, it is faster to increase the error and loop around the boundary
			if (Math.abs(error) > range / 2) {
				if (error > 0) {
					error -= range;
				} else {
					error += range;
				}
			}
		}
		// Calculate the approximation of the error's derivative
		double errorDerivative = (error - lastError) / deltaT;
		// Calculate the approximation of the error's integral
		totalError += error * deltaT;
		// Calculate the result using the PIDF formula
		double result = P * error + I * totalError + D * errorDerivative + F * setpoint;
		// Limit the result to be within the output range [outputMin, outputMax]
		result = Math.max(Math.min(result, outputMax), outputMin);
		// Save the error for calculating future derivatives
		lastError = error;
		if (capOutput) {
			if (result > outputMax) {
				return outputMax;
			} else if (result < outputMin) {
				return outputMin;
			}
		}
		return result;
	}
}
