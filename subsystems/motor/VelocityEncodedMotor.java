package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

public class VelocityEncodedMotor extends VelocitySensorMotor {
	public VelocityEncodedMotor(String name, boolean isInverted, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		super(name, isInverted, slopeController, sensor, motors);
	}
	
	public VelocityEncodedMotor(String name, boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, motors);
	}
	
	public VelocityEncodedMotor(String name, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		this(name, false, slopeController, sensor, motors);
	}
	
	public VelocityEncodedMotor(String name, PIDSource sensor, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, motors);
	}
	
	public VelocityEncodedMotor(boolean isInverted, SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("VelocityEncodedMotor", isInverted, speedModifier, sensor, motors);
	}
	
	public VelocityEncodedMotor(boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this("VelocityEncodedMotor", isInverted, sensor, motors);
	}
	
	public VelocityEncodedMotor(SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("VelocityEncodedMotor", speedModifier, sensor, motors);
	}
	
	public VelocityEncodedMotor(PIDSource sensor, SpeedController... motors) {
		this("VelocityEncodedMotor", sensor, motors);
	}
}
