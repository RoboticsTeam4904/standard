package org.usfirst.frc4904.standard.subsystems.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class VelocitySensorMotor extends SensorMotor {
	public VelocitySensorMotor(String name, boolean isInverted, SpeedModifier slopeController,
			MotionController motionController, MotorController... motors) {
		super(name, isInverted, slopeController, motionController, motors);
	}

	public VelocitySensorMotor(String name, boolean isInverted, MotionController motionController,
			MotorController... motors) {
		this(name, isInverted, new IdentityModifier(), motionController, motors);
	}

	public VelocitySensorMotor(String name, SpeedModifier speedModifier, MotionController motionController,
			MotorController... motors) {
		this(name, false, speedModifier, motionController, motors);
	}

	public VelocitySensorMotor(String name, MotionController motionController, MotorController... motors) {
		this(name, false, new IdentityModifier(), motionController, motors);
	}

	public VelocitySensorMotor(boolean isInverted, SpeedModifier speedModifier, MotionController motionController,
			MotorController... motors) {
		this("VelocitySensorMotor", isInverted, speedModifier, motionController, motors);
	}

	public VelocitySensorMotor(boolean isInverted, MotionController motionController, MotorController... motors) {
		this("VelocitySensorMotor", isInverted, motionController, motors);
	}

	public VelocitySensorMotor(SpeedModifier speedModifier, MotionController motionController,
			MotorController... motors) {
		this("VelocitySensorMotor", speedModifier, motionController, motors);
	}

	public VelocitySensorMotor(MotionController motionController, MotorController... motors) {
		this("VelocitySensorMotor", motionController, motors);
	}

	@Override
	public void set(double speed) {
		if (motionController.isEnabled()) {
			motionController.setSetpoint(speed);
		} else {
			super.set(speed);
		}
	}
}
