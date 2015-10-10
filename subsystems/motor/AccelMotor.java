package org.usfirst.frc4904.standard.subsystems.motor;


import edu.wpi.first.wpilibj.SpeedController;

public class AccelMotor extends Motor {
	private final double maxAccel;
	
	public AccelMotor(String name, SpeedController motor, double maxAccel) {
		super(name, motor);
		this.maxAccel = maxAccel;
	}
	
	public AccelMotor(String name, SpeedController motor, boolean inverted, double maxAccel) {
		super(name, motor, inverted);
		this.maxAccel = maxAccel;
	}
	
	private double capAccel(double speed) {
		// This should reduce the acceleration to maxAccel units
		if (Math.abs(super.get() - speed) > maxAccel) {
			speed = Math.signum(speed) * -1.0 * maxAccel;
		}
		return speed;
	}
	
	public void set(double speed) {
		super.set(capAccel(speed));
	}
	
	public void set(double arg0, byte arg1) {
		super.set(capAccel(arg0), arg1);
	}
}
