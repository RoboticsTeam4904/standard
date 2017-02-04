package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.Util;

public class CapSpeedModifier implements SpeedModifier {
	public final double maxSpeed;
	public final double minSpeed;
	
	public CapSpeedModifier(double maxSpeed, double minSpeed) {
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
	}
	
	@Override
	public double modify(double speed) {
		Util.Range SpeedCap = new Util.Range(minSpeed, maxSpeed);
		return SpeedCap.limitValue(speed);
	}
}
