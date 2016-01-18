package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import edu.wpi.first.wpilibj.SpeedController;

public class AccelMotor extends Motor {
	private double currentSpeed;
	private long lastUpdate;
	private final PDP pdp;
	
	public AccelMotor(String name, SpeedController motor, PDP pdp) {
		super(name, motor);
		this.pdp = pdp;
		currentSpeed = 0;
	}
	
	public AccelMotor(String name, SpeedController motor, boolean inverted, PDP pdp) {
		super(name, motor, inverted);
		this.pdp = pdp;
		currentSpeed = 0;
	}
	
	private double capAccel(double speed) {
		if (Math.abs(speed) > Math.abs(currentSpeed) && pdp.getVoltage() < 11.0) {
			LogKitten.v("Throttling " + super.getName() + " at " + pdp.getVoltage() + " from " + Math.abs(speed) + " to " + Math.abs(currentSpeed));
			speed = currentSpeed;
			if (pdp.getVoltage() < 10.0) {
				speed = currentSpeed - 0.3 * currentSpeed;
			}
		} else if (Math.abs(speed) > Math.abs(currentSpeed)) {
			long deltaT = System.currentTimeMillis() - lastUpdate;
			speed = currentSpeed + ((double) deltaT / (double) 64) * (speed - currentSpeed);
			lastUpdate = System.currentTimeMillis();
		}
		return speed;
	}
	
	public void set(double speed) {
		speed = capAccel(speed);
		currentSpeed = speed;
		super.set(speed);
	}
	
	public void set(double arg0, byte arg1) {
		super.set(capAccel(arg0), arg1);
	}
}
