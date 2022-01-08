package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class CANTalonFX extends WPI_TalonFX implements MotorController, BrakeableMotor {
	protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;

	public CANTalonFX(int deviceNumber, NeutralMode mode) {
		super(deviceNumber);
		setNeutralMode(mode);
	}

	public CANTalonFX(int deviceNumber) {
		this(deviceNumber, DEFAULT_NEUTRAL_MODE);
	}

	public void setCoastMode() {
		setNeutralMode(NeutralMode.Coast);
	}

	public void setBrakeMode() {
		setNeutralMode(NeutralMode.Brake);
	}
}