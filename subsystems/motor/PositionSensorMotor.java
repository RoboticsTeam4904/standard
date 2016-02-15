package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

public class PositionSensorMotor extends SensorMotor {
	public PositionSensorMotor(String name, boolean isInverted, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		super(name, isInverted, slopeController, sensor, false, motors);
	}
	
	public PositionSensorMotor(String name, boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, motors);
	}
	
	public PositionSensorMotor(String name, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		this(name, false, slopeController, sensor, motors);
	}
	
	public PositionSensorMotor(String name, PIDSource sensor, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, motors);
	}
	
	public PositionSensorMotor(boolean isInverted, SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("PositionSensorMotor", isInverted, speedModifier, sensor, motors);
	}
	
	public PositionSensorMotor(boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this("PositionSensorMotor", isInverted, sensor, motors);
	}
	
	public PositionSensorMotor(SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("PositionSensorMotor", speedModifier, sensor, motors);
	}
	
	public PositionSensorMotor(PIDSource sensor, SpeedController... motors) {
		this("PositionSensorMotor", sensor, motors);
	}
	
	@Override
	public void set(double speed) {
		position += speed * (System.currentTimeMillis() - lastUpdate);
		lastUpdate = System.currentTimeMillis();
		pid.setSetpoint(position);
		super.write(speed);
	}
}
