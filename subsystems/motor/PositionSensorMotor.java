package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

public class PositionSensorMotor extends SensorMotor {
	public PositionSensorMotor(String name, boolean isInverted, SpeedModifier speedModifier, MotionController motionController,
		SpeedController... motors) {
		super(name, isInverted, speedModifier, motionController, motors);
	}

	public PositionSensorMotor(String name, boolean isInverted, MotionController motionController, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), motionController, motors);
	}

	public PositionSensorMotor(String name, SpeedModifier speedModifier, MotionController motionController,
		SpeedController... motors) {
		this(name, false, speedModifier, motionController, motors);
	}

	public PositionSensorMotor(String name, MotionController motionController, SpeedController... motors) {
		this(name, false, new IdentityModifier(), motionController, motors);
	}

	public PositionSensorMotor(boolean isInverted, SpeedModifier speedModifier, MotionController motionController,
		SpeedController... motors) {
		this("PositionSensorMotor", isInverted, speedModifier, motionController, motors);
	}

	public PositionSensorMotor(boolean isInverted, MotionController motionController, SpeedController... motors) {
		this("PositionSensorMotor", isInverted, motionController, motors);
	}

	public PositionSensorMotor(SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("PositionSensorMotor", speedModifier, motionController, motors);
	}

	public PositionSensorMotor(MotionController motionController, SpeedController... motors) {
		this("PositionSensorMotor", motionController, motors);
	}
	
	public void setPosition(double position) {
		motionController.setSetpoint(position);
	}
}
