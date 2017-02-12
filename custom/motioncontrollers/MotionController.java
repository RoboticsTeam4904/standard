package org.usfirst.frc4904.standard.custom.motioncontrollers;


import java.util.Timer;
import java.util.TimerTask;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.PIDSensor;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * A MotionController modifies an output using a sensor
 * to precisely maintain a certain input.
 *
 */
public abstract class MotionController {
	protected PIDOutput output;
	protected Timer timer;
	protected MotionControllerTask task;
	protected final PIDSensor sensor;
	protected double setpoint;
	protected double absoluteTolerance;
	protected boolean continuous;
	protected double inputMax;
	protected double inputMin;
	protected boolean capOutput;
	protected double outputMax;
	protected double outputMin;
	protected boolean enable;
	protected boolean overridden;
	protected Exception sensorException;
	private volatile boolean justReset;
	private final Object lock = new Object();

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
		output = null;
		timer = new Timer();
		task = new MotionControllerTask();
		enable = true;
		overridden = false;
		absoluteTolerance = Double.MIN_VALUE; // Nonzero to avoid floating point errors
		capOutput = false;
		continuous = false;
		inputMin = 0.0;
		inputMax = 0.0;
		outputMin = 0.0;
		outputMax = 0.0;
		reset();
		justReset = true;
		sensorException = null;
	}

	/**
	 * Sets the output for this MotionController.
	 * Once every MotionController tick, the output will
	 * be set to the results from the motion control
	 * calculation via the pidWrite function.
	 *
	 * @param output
	 *        The output to control
	 */
	public void setOutput(PIDOutput output) {
		this.output = output;
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
	public final void reset() {
		resetErrorToZero();
		setpoint = sensor.pidGet();
		justReset = true;
	}

	/**
	 * This should return the motion controller
	 * to a state such that it returns 0.
	 */
	public final void resetSafely() throws InvalidSensorException {
		resetErrorToZero();
		setpoint = sensor.pidGetSafely();
		justReset = true;
	}

	/**
	 * Method-specific method for resetting the
	 * motion controller without indicating sensor
	 * errors.
	 */
	protected abstract void resetErrorToZero();

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
	 * Returns the absolute tolerance set on the
	 * motion controller. Will return {@link org.usfirst.frc4904.standard.Util#EPSILON Util.EPSILON}
	 *
	 * @return absolute tolerance
	 */
	public double getAbsoluteTolerance() {
		return absoluteTolerance;
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
		if (isOverridden()) {
			return;
		}
		enable = true;
		try {
			timer.scheduleAtFixedRate(task, 10, 20);
			justReset = true;
			// justReset is written to by both the main thread and the Task,
			// so there is a 10 millisecond delay in the initial execution of
			// the task, which should reduce blocking
		}
		catch (IllegalStateException e) {} // Do not die if the timer is already running
	}

	/**
	 * Bypasses the motion controller.
	 * In some cases, this will still scale by
	 * a feed forward term of the motion controller.
	 */
	public void disable() {
		if (isOverridden()) {
			return;
		}
		enable = false;
		task.cancel();
		timer.purge();
		task = new MotionControllerTask();
		setpoint = sensor.pidGet();
	}

	/**
	 * Is motion control enabled?
	 */
	public boolean isEnabled() {
		return enable;
	}

	/**
	 * Set whether or not the motion controller
	 * is overridden.
	 * 
	 * @see #startOverriding()
	 * @see #stopOverriding()
	 */
	private void setOverride(boolean overridden) {
		this.overridden = overridden;
	}

	/**
	 * Starts overriding the controller.
	 * The controller will disable and not be allowed
	 * to enable until the override is turned off.
	 * 
	 * @see #stopOverriding()
	 */
	public void startOverriding() {
		disable();
		setOverride(true);
	}

	/**
	 * Stops overriding the motion controller.
	 * Enabling the controller will now be allowed.
	 * 
	 * @see #startOverriding()
	 */
	public void stopOverriding() {
		setOverride(false);
	}

	/**
	 * Has the controller been overridden?
	 * 
	 * @see #setOverride(boolean)
	 */
	public boolean isOverridden() {
		return overridden;
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

	/**
	 * Check if the motion controller has generated
	 * an exception within the TimerTask. If there is
	 * not an exception, the function returns null.
	 *
	 * @return the exception (probably null)
	 */
	public Exception checkException() {
		return sensorException;
	}

	/**
	 * The thread in which the output is updated with the
	 * results of the motion controller calculation.
	 *
	 */
	protected class MotionControllerTask extends TimerTask {
		@Override
		public void run() {
			try {
				double value = getSafely(); // Always calculate MC output
				synchronized (lock) {
					if (justReset) {
						justReset = false;
						return;
					}
				}
				if (output != null && isEnabled()) {
					output.pidWrite(value);
				}
			}
			catch (Exception e) {
				sensorException = e;
			}
		}
	}
}
