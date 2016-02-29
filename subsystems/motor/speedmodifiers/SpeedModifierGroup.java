package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


public class SpeedModifierGroup implements SpeedModifier {
	protected SpeedModifier[] speedModifiers;
	
	public SpeedModifierGroup(SpeedModifier... speedModifiers) {
		this.speedModifiers = speedModifiers;
	}
	
	@Override
	public double modify(double speed) {
		double outputSpeed = speed;
		for (SpeedModifier speedModifier : speedModifiers) {
			outputSpeed = speedModifier.modify(outputSpeed);
		}
		return outputSpeed;
	}
}
