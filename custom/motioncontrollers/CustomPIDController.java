package org.usfirst.frc4904.standard.custom.motioncontrollers;


import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * An extremely basic PID controller.
 * It does not differentiate between rate and distance.
 *
 */
public class CustomPIDController extends MotionController {
	protected double P;
	protected double I;
	protected double D;
	protected double F;
	protected double totalError;
	protected double lastError;
	protected boolean justReset;
	
	/**
	 * An extremely basic PID controller.
	 * It does not differentiate between rate and distance.
	 *
	 * @param P
	 *        Initial P constant
	 * @param I
	 *        Initial I constant
	 * @param D
	 *        Initial D constant
	 * @param F
	 *        Initial F (feed forward) constant
	 * @param source
	 *        The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, double F, PIDSource source) {
		super(source);
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
		justReset = true;
	}
	
	/**
	 * An extremely basic PID controller.
	 * It does not differentiate between rate and distance.
	 *
	 * @param P
	 *        Initial P constant
	 * @param I
	 *        Initial I constant
	 * @param D
	 *        Initial D constant
	 * @param source
	 *        The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, PIDSource source) {
		this(P, I, D, 0.0, source);
	}
	
	/**
	 * An extremely basic PID controller.
	 * It does not differentiate between rate and distance.
	 *
	 * @param source
	 *        The sensor linked to the output
	 */
	public CustomPIDController(PIDSource source) {
		this(0, 0, 0, source);
	}
	
	/**
	 * @return
	 * 		The current P value
	 */
	public double getP() {
		return P;
	}
	
	/**
	 * @return
	 * 		The current I value
	 */
	public double getI() {
		return I;
	}
	
	/**
	 * @return
	 * 		The current D value
	 */
	public double getD() {
		return D;
	}
	
	/**
	 * @return
	 * 		The current F (feed forward) value
	 */
	public double getF() {
		return F;
	}
	
	/**
	 * Sets the parameters of the PID loop
	 *
	 * @param P
	 *        Proportional
	 * @param I
	 *        Integral
	 * @param D
	 *        Derivative
	 *
	 *        If you do not know what these mean, please refer
	 *        to this link: https://en.wikipedia.org/wiki/PID_controller
	 */
	public void setPID(double P, double I, double D) {
		this.P = P;
		this.I = I;
		this.D = D;
	}
	
	/**
	 * Sets the parameters of the PID loop
	 *
	 * @param P
	 *        Proportional
	 * @param I
	 *        Integral
	 * @param D
	 *        Derivative
	 * @param F
	 *        Feed forward (scalar on input added to output)
	 * 
	 *        If you do not know what these mean, please refer
	 *        to this link: https://en.wikipedia.org/wiki/PID_controller
	 */
	public void setPIDF(double P, double I, double D, double F) {
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
	}
	
	/**
	 * Resets the PID controller.
	 * This sets total error and last error to 0,
	 * as well as setting the setpoint to the current
	 * sensor reading.
	 */
	@Override
	public void reset() {
		setpoint = source.pidGet();
		totalError = 0;
		lastError = 0;
		justReset = true;
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
		double errorDerivative;
		// If we've just reset the error could jump from 0 to a very high number so just return 0
		if (justReset) {
			errorDerivative = 0;
		} else {
			errorDerivative = (error - lastError);
		}
		// Calculate the approximation of the error's integral
		totalError += error;
		// Calculate the result using the PIDF formula
		double result = P * error + I * totalError + D * errorDerivative + F * setpoint;
		// Save the error for calculating future derivatives
		lastError = error;
		justReset = false;
		LogKitten.v(input + " " + setpoint + " " + result);
		if (capOutput) {
			// Limit the result to be within the output range [outputMin, outputMax]
			result = Math.max(Math.min(result, outputMax), outputMin);
		}
		return result;
	}
	
	@Override
	public boolean onTarget() {
		return !justReset && super.onTarget();
	}
}
