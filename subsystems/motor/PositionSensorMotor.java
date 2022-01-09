package org.usfirst.frc4904.standard.subsystems.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class PositionSensorMotor extends SensorMotor {
	public PositionSensorMotor(String name, boolean isInverted, SpeedModifier speedModifier,
			MotionController motionController, MotorController... motors) {
		super(name, isInverted, speedModifier, motionController, motors);
		setName(name);
	}

	public PositionSensorMotor(String name, boolean isInverted, MotionController motionController,
			MotorController... motors) {
		this(name, isInverted, new IdentityModifier(), motionController, motors);
	}

	public PositionSensorMotor(String name, SpeedModifier speedModifier, MotionController motionController,
			MotorController... motors) {
		this(name, false, speedModifier, motionController, motors);
	}

	public PositionSensorMotor(String name, MotionController motionController, MotorController... motors) {
		this(name, false, new IdentityModifier(), motionController, motors);
	}

	public PositionSensorMotor(boolean isInverted, SpeedModifier speedModifier, MotionController motionController,
			MotorController... motors) {
		this("PositionSensorMotor", isInverted, speedModifier, motionController, motors);
	}

	public PositionSensorMotor(boolean isInverted, MotionController motionController, MotorController... motors) {
		this("PositionSensorMotor", isInverted, motionController, motors);
	}

	public PositionSensorMotor(SpeedModifier speedModifier, MotionController motionController,
			MotorController... motors) {
		this("PositionSensorMotor", speedModifier, motionController, motors);
	}

	public PositionSensorMotor(MotionController motionController, MotorController... motors) {
		this("PositionSensorMotor", motionController, motors);
	}

	public void setPosition(double position) {
		motionController.setSetpoint(position);
	}
}
