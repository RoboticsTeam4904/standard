package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotorController;

public class CANTalonFX extends WPI_TalonFX implements BrakeableMotorController {
	protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  = InvertType.FollowMaster;

	// TODO: implement setVoltage with native APIs

	/**
	 * Represents a Falcon motor in code. You probably want NeutralMode.Brake, InvertType.FollowMaster
	 * TODO document better
	 * 
	 * @param deviceNumber
	 * @param neutralMode
	 * @param inverted
	 */
	public CANTalonFX(int deviceNumber, NeutralMode neutralMode, InvertType inverted) {
		super(deviceNumber);
		configFactoryDefault();	// use default settings to prevent unexpected behavior, reccommended in examples
		setNeutralMode(neutralMode);
		setInverted(inverted);
	}

	public void setCoastMode() {
		setNeutralMode(NeutralMode.Coast);
	}

	public void setBrakeMode() {
		setNeutralMode(NeutralMode.Brake);
	}
}