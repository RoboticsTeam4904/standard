package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An accel motor is a motor that is acceleration limited
 * to prevent brownouts. This is done by generally creating
 * an acceleration curve over 1/16th of a second (e.g.
 * increasing the speed by 1/16th of the requested increase
 * per 1/16th of a second). In addition, if the battery
 * voltage as measured by the PDP decreases below 11.0
 * volts, the motor will begin to throttle its speed.
 * Once the voltage is below 10.0 volts, the motor will
 * start actively slowing down.
 * 
 * If you are considering having multiple mechanically
 * connected motors being acceleration limited, it is
 * recommended that you put the motors into a motor
 * group first.
 *
 */
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
		LogKitten.d("Inits:  " + Double.toString(speed));
		if (Math.abs(speed) > Math.abs(currentSpeed) && pdp.getVoltage() < 11.0) {
			LogKitten.d("Throttling " + super.getName() + " at " + pdp.getVoltage() + " from " + Math.abs(speed) + " to " + Math.abs(currentSpeed));
			speed = currentSpeed;
			if (pdp.getVoltage() < 10.0) {
				speed = currentSpeed - 0.3 * currentSpeed;
			}
		} else if (Math.abs(speed) > Math.abs(currentSpeed)) {
			long deltaT = System.currentTimeMillis() - lastUpdate;
			LogKitten.d("Delta T " + Long.toString(deltaT));
			speed = currentSpeed + ((double) deltaT / (double) 64) * (speed - currentSpeed);
			lastUpdate = System.currentTimeMillis();
		}
		LogKitten.d("Speed:  " + Double.toString(speed));
		return speed;
	}
	
	/**
	 * Sets motor speed with throttling.
	 */
	public void set(double speed) {
		speed = capAccel(speed);
		currentSpeed = speed;
		super.set(speed);
	}
	
	public void set(double arg0, byte arg1) {
		super.set(capAccel(arg0), arg1);
	}
}
