package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An encoded motor is a motor with a set of variables relevant to controlling a motor with an encoder.
 * It contains an Encoder, PID constants, and range information.
 */
public class PositionEncodedMotor extends PositionSensorMotor {
	public PositionEncodedMotor(String name, boolean isInverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		super(name, isInverted, speedModifier, motionController, motors);
	}
	
	public PositionEncodedMotor(String name, boolean isInverted, MotionController motionController, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), motionController, motors);
	}
	
	public PositionEncodedMotor(String name, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this(name, false, speedModifier, motionController, motors);
	}
	
	public PositionEncodedMotor(String name, MotionController motionController, SpeedController... motors) {
		this(name, false, new IdentityModifier(), motionController, motors);
	}
	
	public PositionEncodedMotor(boolean isInverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("PositionEncodedMotor", isInverted, speedModifier, motionController, motors);
	}
	
	public PositionEncodedMotor(boolean isInverted, MotionController motionController, SpeedController... motors) {
		this("PositionEncodedMotor", isInverted, motionController, motors);
	}
	
	public PositionEncodedMotor(SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("PositionEncodedMotor", speedModifier, motionController, motors);
	}
	
	public PositionEncodedMotor(MotionController motionController, SpeedController... motors) {
		this("PositionEncodedMotor", motionController, motors);
	}
}
