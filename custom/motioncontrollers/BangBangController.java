package org.usfirst.frc4904.standard.custom.motioncontrollers;


import edu.wpi.first.wpilibj.PIDSource;

public class BangBangController extends MotionController {
	protected double error;
	protected double A;
	protected double F;
	
	/**
	 * BangBang controller
	 * 
	 * @param source
	 *        Sensor
	 * @param A
	 *        Adjustment term
	 * @param F
	 *        Feedforward term
	 */
	public BangBangController(PIDSource source, double A, double F) {
		super(source);
		this.A = A;
		this.F = F;
		reset();
	}
	
	@Override
	public void reset() {
		setpoint = source.pidGet();
		error = 0;
	}
	
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
	
	@Override
	public double getError() {
		return error;
	}
}
