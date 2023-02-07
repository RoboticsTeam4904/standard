package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotorController;

public class CANTalonFX extends WPI_TalonFX implements BrakeableMotorController {
	protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;

	// TODO: implement setVoltage with native APIs

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