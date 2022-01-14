// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.motioncontrollers;

import java.util.function.DoubleConsumer;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.NativeDerivativeSensor;
import org.usfirst.frc4904.standard.custom.sensors.PIDSensor;

import org.usfirst.frc4904.standard.custom.CustomPIDSourceType;
import org.usfirst.frc4904.standard.custom.motioncontrollers.CustomPIDController;
import edu.wpi.first.hal.util.BoundaryException;

/**
 * An extremely basic PID controller. It does not differentiate between rate and
 * distance.
 *
 */
public class CustomPIDController extends MotionController {
	protected double P;
	protected double I;
	protected double IPrime = 0.0;
	protected double D;
	protected double F;
	protected double integralThreshold = 0.0;
	protected double totalError;
	protected double lastError;
	protected double accumulatedOutput;
	protected long lastTime;
	protected double lastErrorDerivative;
	protected double derivativeTolerance = Double.MIN_VALUE;
	protected double minimumNominalOutput = 0.0;

	/**
	 * An extremely basic PID controller. It does not differentiate between rate and
	 * distance.
	 *
	 * @param P      Initial P constant
	 * @param I      Initial I constant
	 * @param D      Initial D constant
	 * @param F      Initial F (feed forward) constant
	 * @param sensor The sensor linked to the output
	 * @param output A motor that accepts PID controller inputs
	 */
	public CustomPIDController(double P, double I, double D, double F, PIDSensor sensor, DoubleConsumer output) {
		super(sensor, output);
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
	}

	/**
	 * An extremely basic PID controller. It does not differentiate between rate and
	 * distance.
	 *
	 * @param P      Initial P constant
	 * @param I      Initial I constant
	 * @param D      Initial D constant
	 * @param F      Initial F (feed forward) constant
	 * @param sensor The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, double F, PIDSensor sensor) {
		super(sensor);
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
	}

	/**
	 * An extremely basic PID controller. It does not differentiate between rate and
	 * distance.
	 *
	 * @param P      Initial P constant
	 * @param I      Initial I constant
	 * @param D      Initial D constant
	 * @param F      Initial F (feed forward) constant
	 * @param sensor The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, double F, PIDSensor sensor, double integralThreshold) {
		this(P, I, D, F, sensor);
		this.setIThreshold(integralThreshold);
	}

	/**
	 * An extremely basic PID controller. It does not differentiate between rate and
	 * distance.
	 *
	 * @param P      Initial P constant
	 * @param I      Initial I constant
	 * @param D      Initial D constant
	 * @param sensor The sensor linked to the output
	 */
	public CustomPIDController(double P, double I, double D, PIDSensor sensor) {
		super(sensor);
		this.P = P;
		this.I = I;
		this.D = D;
	}

	/**
	 * An extremely basic PID controller. It does not differentiate between rate and
	 * distance.
	 *
	 * @param sensor The sensor linked to the output
	 */
	public CustomPIDController(PIDSensor sensor) {
		this(0, 0, 0, sensor);
	}

	/**
	 * @return The current P value
	 */
	public double getP() {
		return P;
	}

	/**
	 * @return The current I value
	 */
	public double getI() {
		return I;
	}

	/**
	 * @return The current D value
	 */
	public double getD() {
		return D;
	}

	/**
	 * @return The current F (feed forward) value
	 */
	public double getF() {
		return F;
	}

	/**
	 * @return The current I' (ouput integral) value
	 */
	public double getIPrime() {
		return IPrime;
	}

	/**
	 * @param IPrime Integral of the PID output
	 */
	public void setIPrime(double IPrime) {
		this.IPrime = IPrime;
	}

	/**
	 *
	 * @return The current minimumNominalOutput (minimum nominal output) value
	 */
	public double getMinimumNominalOutput() {
		return minimumNominalOutput;
	}

	/**
	 * Sets the parameters of the PID loop
	 *
	 * @param P Proportional
	 * @param I Integral
	 * @param D Derivative
	 *
	 *          If you do not know what these mean, please refer to this link:
	 *          https://en.wikipedia.org/wiki/PID_controller
	 */
	public void setPID(double P, double I, double D) {
		this.P = P;
		this.I = I;
		this.D = D;
	}

	/**
	 * Sets the parameters of the PID loop
	 *
	 * @param P Proportional
	 * @param I Integral
	 * @param D Derivative
	 * @param F Feed forward (scalar on input added to output)
	 *
	 *          If you do not know what these mean, please refer to this link:
	 *          https://en.wikipedia.org/wiki/PID_controller
	 */
	public void setPIDF(double P, double I, double D, double F) {
		this.P = P;
		this.I = I;
		this.D = D;
		this.F = F;
	}

	/**
	 * Set the maximum derivative value at which the controller can be considered
	 * "on-target," given that the source suggests that the setpoint has been
	 * reached. Set this value to a higher value if overshoot due to high velocity
	 * is a problem.
	 *
	 * @param derivativeTolerance the maximum derivative value for onTarget() to
	 *                            return true
	 */
	public void setDerivativeTolerance(double derivativeTolerance) {
		this.derivativeTolerance = derivativeTolerance;
	}

	/**
	 * Get the absolute derivative value stop condition.
	 *
	 * @see #setDerivativeTolerance(double)
	 * @return the maximum derivative value for onTarget() to return true
	 */
	public double getDerivativeTolerance() {
		return derivativeTolerance;
	}

	/**
	 * 
	 * @param minimumNominalOutput Minimum Nominal Output result will be set to
	 *                             ±this value if the absolute value of the result
	 *                             is less than this value. This is useful if the
	 *                             motor can only run well above a value.
	 */
	public void setMinimumNominalOutput(double minimumNominalOutput) {
		this.minimumNominalOutput = minimumNominalOutput;
	}

	/**
	 * Sets the threshold below which the I term becomes active. When the I term is
	 * active, the error sum increases. When the I term is not active, the error sum
	 * is set to zero.
	 * 
	 * @param integralThreshold
	 */
	public void setIThreshold(double integralThreshold) {
		if (integralThreshold < 0) {
			throw new BoundaryException("I threshold negative");
		}
		this.integralThreshold = integralThreshold;
	}

	/**
	 * Gets the threshold below which the I term becomes active. When the I term is
	 * active, the error sum increases. When the I term is not active, the error sum
	 * is set to zero.
	 * 
	 * @return
	 */
	public double getIThreshold() {
		return integralThreshold;
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
	 * Feedforward constant. If a position controller, the F constant is multiplied
	 * by the sign of the setpoint. If a velocity controller, F is multiplied by
	 * setpoint.
	 * 
	 * @return the feedforward value for the given setpoint.
	 */
	// private double feedForward() {
	// 	if (sensor.getCustomPIDSourceType() == CustomPIDSourceType.kDisplacement) {
	// 		return F * Math.signum(lastError);
	// 	} else {
	// 		return F * setpoint;
	// 	}
	// }

	/**
	 * Get the current output of the PID loop. This should be used to set the output
	 * (like a Motor).
	 *
	 * @return The current output of the PID loop.
	 * @throws InvalidSensorException when a sensor fails
	 */
	@Override
	public double getSafely() throws InvalidSensorException {
		// If PID is not enabled, use feedforward only
		if (!isEnabled()) {
			// return feedForward();
		}
		double input = 0.0;
		input = sensor.pidGet();
		double error = setpoint - input;
		// Account for continuous input ranges
		if (continuous) {
			double range = inputMax - inputMin;
			// If the error is more than half of the range, it is faster to increase the
			// error and loop around the boundary
			if (Math.abs(error) > range / 2) {
				if (error > 0) {
					error -= range;
				} else {
					error += range;
				}
			}
		}
		long latestTime = System.currentTimeMillis();
		long timeDiff = latestTime - lastTime;
		lastTime = latestTime;
		// If we just reset, then the lastTime could be way before the latestTime and so
		// timeDiff would be huge.
		// This would lead to a very big I (and a big D, briefly).
		// Also, D could be unpredictable because lastError could be wildly different
		// than error (since they're
		// separated by more than a tick in time).
		// Hence, if we just reset, just pretend we're still disabled and record the
		// lastTime and lastError for next tick.
		if (didJustReset()) {
			lastError = error;
			// return feedForward();
		}
		// Check if the sensor supports native derivative calculations and that we're
		// doing displacement PID
		// (if we're doing rate PID, then getRate() would be the PID input rather then
		// the input's derivative)
		double errorDerivative;
		if (sensor instanceof NativeDerivativeSensor
				&& sensor.getCustomPIDSourceType() == CustomPIDSourceType.kDisplacement) {
			errorDerivative = ((NativeDerivativeSensor) sensor).getRateSafely();
		} else {
			// Calculate the approximation of the derivative.
			errorDerivative = (error - lastError) / timeDiff;
		}
		if (integralThreshold == 0 || Math.abs(error) < integralThreshold) {
			// Calculate the approximation of the error's integral
			totalError += error * timeDiff;
		} else {// if (error/Math.abs(error) == -totalError/Math.abs(totalError)){
			totalError = 0.0;
		}
		// Calculate the result using the PIDF formula
		double PIDresult = P * error + I * totalError + D * errorDerivative + F * ((sensor.getCustomPIDSourceType() == CustomPIDSourceType.kDisplacement) ? Math.signum(error) : setpoint);
		double output = PIDresult + IPrime * accumulatedOutput;
		accumulatedOutput += PIDresult * timeDiff;
		// Save the error for calculating future derivatives
		lastError = error;
		lastErrorDerivative = errorDerivative;
		if (capOutput) {
			// Limit the result to be within the output range [outputMin, outputMax]
			output = Math.max(Math.min(output, outputMax), outputMin);
		}
		if (Math.abs(output) < minimumNominalOutput) {
			output = Math.signum(output) * minimumNominalOutput;
		}
		return output;
	}

	/**
	 * Get the current output of the PID loop. This should be used to set the output
	 * (like a Motor).
	 *
	 * @return The current output of the PID loop.
	 * @warning does not indicate sensor error
	 */
	@Override
	public double get() {
		try {
			return getSafely();
		} catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
	}

	public boolean derivativeOnTarget() {
		return derivativeTolerance == 0 || Math.abs(lastErrorDerivative) < derivativeTolerance;
	}

	@Override
	public boolean onTarget() {
		return super.onTarget() && derivativeOnTarget();
	}
}
