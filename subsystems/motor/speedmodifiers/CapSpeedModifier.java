package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


public class CapSpeedModifier implements SpeedModifier {
	public final double maxSpeed;
	public final double minSpeed;
	
	public CapSpeedModifier(double max, double min) {
		maxSpeed = max;
		minSpeed = min;
	}
	
	@Override
	public double modify(double speed) {
		// TODO Auto-generated method stub
		if (speed > maxSpeed) {
			speed = maxSpeed;
		}
		if (speed < minSpeed) {
			speed = minSpeed;
		}
		return speed;
	}
}
