package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.Util;

public class CapSpeedModifier implements SpeedModifier {
	protected final Util.Range speedCap;

	public CapSpeedModifier(Util.Range speedCap) {
		this.speedCap = speedCap;
	}

	public CapSpeedModifier(double minSpeed, double maxSpeed) {
		this(new Util.Range(minSpeed, maxSpeed));
	}

	@Override
	public double modify(double speed) {
		return speedCap.limitValue(speed);
	}
}
