package org.usfirst.frc4904.standard.custom.motioncontrollers;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.NativeDerivativeSensor;
import org.usfirst.frc4904.standard.custom.sensors.PIDSensor;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

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
	protected long lastTime;
	protected double minimumNominalOutput = 0.0;

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
	 * @param sensor
	 *        The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, double F, PIDSensor sensor) {
		super(sensor);
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
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
	 * @param F
	 *        Initial F (feed forward) constant
	 * @param sensor
	 *        The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, double F, PIDSource source) {
		super(source);
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
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
	 * @param sensor
	 *        The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, PIDSensor sensor) {
		this(P, I, D, 0.0, sensor);
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
	 * @param sensor
	 *        The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, PIDSource source) {
		this(P, I, D, 0.0, source);
	}

	/**
	 * An extremely basic PID controller.
	 * It does not differentiate between rate and distance.
	 *
	 * @param sensor
	 *        The sensor linked to the output
	 */
	public CustomPIDController(PIDSensor sensor) {
		this(0, 0, 0, sensor);
	}

	/**
	 * An extremely basic PID controller.
	 * It does not differentiate between rate and distance.
	 *
	 * @param sensor
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
	 *
	 * @return
	 * 		The current minimumNominalOutput (minimum nominal output) value
	 */
	public double getMinimumNominalOutput() {
		return minimumNominalOutput;
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
	 * 
	 * @param minimumNominalOutput
	 *        Minimum Nominal Output
	 *        result will be set to
	 *        ±this value if the absolute
	 *        value of the result is less than
	 *        this value. This is useful if
	 *        the motor can only run well above a value.
	 */
	public void setMinimumNominalOutput(double minimumNominalOutput) {
		this.minimumNominalOutput = minimumNominalOutput;
	}

	/**
	 * Resets the PID controller error to zero.
	 */
	@Override
	protected void resetErrorToZero() {
		totalError = 0;
		lastError = 0;
	}

	@Override
	public double getError() {
		return lastError;
	}

	/**
	 * Get the current output of the PID loop.
	 * This should be used to set the output (like a Motor).
	 *
	 * @return The current output of the PID loop.
	 * @throws InvalidSensorException
	 *         when a sensor fails
	 */
	@Override
	public double getSafely() throws InvalidSensorException {
		// If PID is not enabled, use feedforward only
		if (!isEnabled()) {
			return F * setpoint;
		}
		double input = 0.0;
		input = sensor.pidGet();
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
		double errorDerivative;
		long latestTime = System.currentTimeMillis();
		long timeDiff = latestTime - lastTime;
		// Check if the sensor supports native derivative calculations and that we're doing displacement PID
		// (if we're doing rate PID, then getRate() would be the PID input rather then the input's derivative)
		if (sensor instanceof NativeDerivativeSensor && sensor.getPIDSourceType() == PIDSourceType.kDisplacement) {
			errorDerivative = ((NativeDerivativeSensor) sensor).getRateSafely();
		} else {
			// Calculate the approximation of the derivative.
			errorDerivative = (error - lastError) / timeDiff;
		}
		lastTime = latestTime;
		// Calculate the approximation of the error's integral
		totalError += error * timeDiff;
		// Calculate the result using the PIDF formula
		double result = P * error + I * totalError + D * errorDerivative + F * setpoint;
		// Save the error for calculating future derivatives
		lastError = error;
		LogKitten.v(input + " " + setpoint + " " + result);
		if (capOutput) {
			// Limit the result to be within the output range [outputMin, outputMax]
			result = Math.max(Math.min(result, outputMax), outputMin);
		}
		if (Math.abs(result) < minimumNominalOutput) {
			result = Math.signum(result) * minimumNominalOutput;
		}
		return result;
	}

	/**
	 * Get the current output of the PID loop.
	 * This should be used to set the output (like a Motor).
	 *
	 * @return The current output of the PID loop.
	 * @warning does not indicate sensor error
	 */
	@Override
	public double get() {
		try {
			return getSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
	}
}
