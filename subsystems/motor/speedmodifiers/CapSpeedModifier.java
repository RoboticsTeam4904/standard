package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.Util;

public class CapSpeedModifier implements SpeedModifier {
	public final Util.Range speedCap;
	
	public CapSpeedModifier(double minSpeed, double maxSpeed) {
		speedCap = new Util.Range(minSpeed, maxSpeed);
	}
	
	@Override
	public double modify(double speed) {
		return speedCap.limitValue(speed);
	}
}
