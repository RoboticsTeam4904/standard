package org.usfirst.frc4904.standard.custom.motioncontrollers;


import edu.wpi.first.wpilibj.PIDSource;

/**
 * A bang bang controller.
 * The bang bang controller increases the value of the output
 * if it is below the setpoint or decreases the value of the
 * output if it is above the setpoint.
 *
 */
public class BangBangController extends MotionController {
	protected double error;
	protected double A;
	protected double F;
	
	/**
	 * BangBang controller
	 * A bang bang controller.
	 * The bang bang controller increases the value of the output
	 * if it is below the setpoint or decreases the value of the
	 * output if it is above the setpoint.
	 * 
	 * @param source
	 *        Sensor
	 * @param A
	 *        Adjustment term
	 *        The is the amount the setpoint is increase
	 *        or decrease by.
	 * @param F
	 *        Feedforward term
	 *        The scalar on the input.
	 */
	public BangBangController(PIDSource source, double A, double F) {
		super(source);
		this.A = A;
		this.F = F;
		reset();
	}
	
	/**
	 * Sets the setpoint to the current sensor value.
	 */
	@Override
	public void reset() {
		setpoint = source.pidGet();
		error = 0;
	}
	
	/**
	 * Get the current output of the bang bang controller.
	 * This should be used to set the output.
	 * 
	 * @return
	 * 		The current output of the bang bang controller.
	 */
	@Override
	public double get() {
		if (!enable) {
			return F * setpoint;
		}
		double input = source.pidGet();
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
		if (error < 0) {
			return A + F * setpoint;
		} else if (error > 0) {
			return -(A + F * setpoint);
		}
		return F * setpoint;
	}
	
	/**
	 * @return
	 * 		The most recent error.
	 */
	@Override
	public double getError() {
		return error;
	}
}
