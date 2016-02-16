package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

public class VelocitySensorMotor extends SensorMotor {
	public VelocitySensorMotor(String name, boolean isInverted, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		super(name, isInverted, slopeController, sensor, true, motors);
	}
	
	public VelocitySensorMotor(String name, boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, motors);
	}
	
	public VelocitySensorMotor(String name, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		this(name, false, slopeController, sensor, motors);
	}
	
	public VelocitySensorMotor(String name, PIDSource sensor, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, motors);
	}
	
	public VelocitySensorMotor(boolean isInverted, SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("VelocitySensorMotor", isInverted, speedModifier, sensor, motors);
	}
	
	public VelocitySensorMotor(boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this("VelocitySensorMotor", isInverted, sensor, motors);
	}
	
	public VelocitySensorMotor(SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("VelocitySensorMotor", speedModifier, sensor, motors);
	}
	
	public VelocitySensorMotor(PIDSource sensor, SpeedController... motors) {
		this("VelocitySensorMotor", sensor, motors);
	}
	
	public void set(double speed) {
		LogKitten.v(speed + "");
		pid.setSetpoint(speed);
		super.write(speed);
	}
}
