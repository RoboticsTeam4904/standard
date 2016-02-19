package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

public class VelocityEncodedMotor extends VelocitySensorMotor {
	public VelocityEncodedMotor(String name, boolean isInverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		super(name, isInverted, speedModifier, motionController, motors);
	}
	
	public VelocityEncodedMotor(String name, boolean isInverted, MotionController motionController, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), motionController, motors);
	}
	
	public VelocityEncodedMotor(String name, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this(name, false, speedModifier, motionController, motors);
	}
	
	public VelocityEncodedMotor(String name, MotionController motionController, SpeedController... motors) {
		this(name, false, new IdentityModifier(), motionController, motors);
	}
	
	public VelocityEncodedMotor(boolean isInverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("VelocityEncodedMotor", isInverted, speedModifier, motionController, motors);
	}
	
	public VelocityEncodedMotor(boolean isInverted, MotionController motionController, SpeedController... motors) {
		this("VelocityEncodedMotor", isInverted, motionController, motors);
	}
	
	public VelocityEncodedMotor(SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("VelocityEncodedMotor", speedModifier, motionController, motors);
	}
	
	public VelocityEncodedMotor(MotionController motionController, SpeedController... motors) {
		this("VelocityEncodedMotor", motionController, motors);
	}
}
