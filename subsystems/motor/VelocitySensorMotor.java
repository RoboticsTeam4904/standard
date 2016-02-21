package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

public class VelocitySensorMotor extends SensorMotor {
	public VelocitySensorMotor(String name, boolean isInverted, SpeedModifier slopeController, MotionController motionController, SpeedController... motors) {
		super(name, isInverted, slopeController, motionController, motors);
	}
	
	public VelocitySensorMotor(String name, boolean isInverted, MotionController motionController, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), motionController, motors);
	}
	
	public VelocitySensorMotor(String name, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this(name, false, speedModifier, motionController, motors);
	}
	
	public VelocitySensorMotor(String name, MotionController motionController, SpeedController... motors) {
		this(name, false, new IdentityModifier(), motionController, motors);
	}
	
	public VelocitySensorMotor(boolean isInverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("VelocitySensorMotor", isInverted, speedModifier, motionController, motors);
	}
	
	public VelocitySensorMotor(boolean isInverted, MotionController motionController, SpeedController... motors) {
		this("VelocitySensorMotor", isInverted, motionController, motors);
	}
	
	public VelocitySensorMotor(SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("VelocitySensorMotor", speedModifier, motionController, motors);
	}
	
	public VelocitySensorMotor(MotionController motionController, SpeedController... motors) {
		this("VelocitySensorMotor", motionController, motors);
	}
}
