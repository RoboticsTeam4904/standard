package org.usfirst.frc4904.standard.custom.motioncontrollers;


import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * A MotionController modifies an output using a sensor
 * to precisely maintain a certain input.
 *
 */
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
	
	/**
	 * A MotionController modifies an output using a sensor
	 * to precisely maintain a certain input.
	 *
	 * @param source
	 *        The sensor associated with the output you are
	 *        trying to control
	 */
	public MotionController(PIDSource source) {
		this.source = source;
		enable = true;
		absoluteTolerance = Util.EPSILON; // Nonzero to avoid floating point errors
		capOutput = false;
		continuous = false;
		inputMin = 0.0;
		inputMax = 0.0;
		outputMin = 0.0;
		outputMax = 0.0;
		reset();
	}
	
	/**
	 * This should return the motion controller
	 * to a state such that it returns 0.
	 */
	public abstract void reset();
	
	/**
	 * The calculated output value to achieve the
	 * current setpoint.
	 *
	 * @return
	 * 		Output value. If output range is set,
	 *         this will be restricted to within
	 *         that range.
	 */
	public abstract double get();
	
	/**
	 * A very recent error.
	 *
	 * @return
	 * 		The most recent error calculated by
	 *         the get function.
	 */
	public abstract double getError();
	
	/**
	 * The most recent setpoint.
	 *
	 * @return
	 * 		The most recent setpoint.
	 */
	public double getSetpoint() {
		return setpoint;
	}
	
	/**
	 * Sets the setpoint of the motion controller.
	 * This is the value that the motion controller seeks.
	 *
	 * @param setpoint
	 */
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}
	
	/**
	 * Sets the tolerance of the motion controller.
	 * When the error is less than the tolerance,
	 * onTarget returns true.
	 *
	 * @param absoluteTolerance
	 */
	public void setAbsoluteTolerance(double absoluteTolerance) {
		if (absoluteTolerance >= 0) {
			this.absoluteTolerance = absoluteTolerance;
			return;
		}
		throw new BoundaryException("Absolute tolerance negative");
	}
	
	/**
	 * Sets the input range of the motion controller.
	 * This is only used to work with continuous inputs.
	 * If minimum is greater than maximum, this will throw
	 * an exception.
	 *
	 * @param minimum
	 * @param maximum
	 */
	public void setInputRange(double minimum, double maximum) {
		if (minimum > maximum) {
			throw new BoundaryException("Minimum is greater than maximum");
		}
		inputMin = minimum;
		inputMax = maximum;
	}
	
	/**
	 * Sets the output range of the motion controller.
	 * Results from the motion control calculation will be
	 * capped at these values. The cap is automatically
	 * enabled by calling this function.
	 *
	 * @param minimum
	 * @param maximum
	 */
	public void setOutputRange(double minimum, double maximum) {
		outputMin = minimum;
		outputMax = maximum;
		capOutput = true;
	}
	
	/**
	 * Stops capping the output range.
	 */
	public void disableOutputRange() {
		capOutput = false;
	}
	
	/**
	 * Sets the input range to continuous.
	 * This means that it will treat the
	 * maximum and minimum sensor and input values as
	 * being at the same point, e.g. the controller
	 * will try to pass through the maximum
	 * to get to a point beyond it.
	 *
	 * @param continuous
	 */
	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
	}
	
	/**
	 * Turns on the motion controller.
	 */
	public void enable() {
		enable = true;
	}
	
	/**
	 * Bypasses the motion controller.
	 * In some cases, this will still scale by
	 * a feed forward term of the motion controller.
	 */
	public void disable() {
		enable = false;
	}
	
	/**
	 * True if the error in the motion controller is
	 * less than the tolerance of the motion controller.
	 *
	 * @return
	 * 		^^
	 */
	public boolean onTarget() {
		return Math.abs(getError()) <= absoluteTolerance;
	}
}
