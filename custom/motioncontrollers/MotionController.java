package org.usfirst.frc4904.standard.custom.motioncontrollers;


import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.PIDSensor;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * A MotionController modifies an output using a sensor
 * to precisely maintain a certain input.
 *
 */
public abstract class MotionController {
	protected final PIDSensor sensor;
	protected double setpoint;
	protected double absoluteTolerance;
	protected boolean continuous;
	protected Util.Range inputRange;
	protected boolean capOutput;
	protected double outputMax;
	protected double outputMin;
	protected boolean enable;
	
	/**
	 * A MotionController modifies an output using a sensor
	 * to precisely maintain a certain input.
	 *
	 * @param sensor
	 *        The sensor associated with the output you are
	 *        trying to control
	 */
	public MotionController(PIDSensor sensor) {
		this.sensor = sensor;
		enable = true;
		absoluteTolerance = Util.EPSILON; // Nonzero to avoid floating point errors
		capOutput = false;
		continuous = false;
		inputRange = new Util.Range(0, 0);
		outputMin = 0.0;
		outputMax = 0.0;
		reset();
	}

	/**
	 * A MotionController modifies an output using a sensor
	 * to precisely maintain a certain input.
	 *
	 * @param sensor
	 *        The sensor associated with the output you are
	 *        trying to control
	 */
	public MotionController(PIDSource source) {
		this(new PIDSensor.PIDSourceWrapper(source));
	}

	/**
	 * This should return the motion controller
	 * to a state such that it returns 0.
	 *
	 * @warning this does not indicate sensor errors
	 */
	public abstract void reset();

	/**
	 * This should return the motion controller
	 * to a state such that it returns 0.
	 */
	public abstract void resetSafely() throws InvalidSensorException;
	
	/**
	 * The calculated output value to achieve the
	 * current setpoint.
	 *
	 * @return
	 * 		Output value. If output range is set,
	 *         this will be restricted to within
	 *         that range.
	 */
	public abstract double getSafely() throws InvalidSensorException;
	
	/**
	 * The calculated output value to achieve the
	 * current setpoint.
	 *
	 * @return
	 * 		Output value. If output range is set,
	 *         this will be restricted to within
	 *         that range.
	 * @warning does not indicate sensor errors
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
	 * Sets the input range of the motion controller to a
	 * new instance of Util.Range with the specified boundaries.
	 * This is only used to work with continuous inputs.
	 * If minimum is greater than maximum, this will throw
	 * an exception.
	 *
	 * @param minimum
	 * @param maximum
	 */
	public void setInputRange(double minimum, double maximum) throws BoundaryException {
		setInputRange(new Util.Range(minimum, maximum));
	}
	
	/**
	 * Sets the input range of the motion controller to an
	 * existing instance of Util.Range. This is only used
	 * to work with continuous inputs. If minimum is
	 * greater than maximum, this will throw an exception.
	 *
	 * @param range
	 */
	public void setInputRange(Util.Range range) {
		inputRange = range;
	}
	
	/**
	 * Get the input range of the MotionController.
	 *
	 * @param range
	 */
	public Util.Range getInputRange() {
		return inputRange;
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
	 * Set the PIDSourceType (rate or displacement)
	 * of this input sensor.
	 * 
	 * @param sourceType
	 */
	public void setSensorSourceType(PIDSourceType sourceType) {
		sensor.setPIDSourceType(sourceType);
	}

	/**
	 * Get the currently set PIDSourceType of the input sensor.
	 * 
	 * @return PIDSourceType
	 */
	public PIDSourceType getSensorSourceType() {
		return sensor.getPIDSourceType();
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
		setpoint = sensor.pidGet();
	}
	
	/**
	 * True if the error in the motion controller is
	 * less than the tolerance of the motion controller.
	 *
	 * @return
	 */
	public boolean onTarget() {
		return Math.abs(getError()) <= absoluteTolerance;
	}
}
