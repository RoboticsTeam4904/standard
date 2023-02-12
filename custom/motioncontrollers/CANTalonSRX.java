// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotorController;
public class CANTalonSRX extends WPI_TalonSRX implements BrakeableMotorController {
	protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;

	// TODO: implement setVoltage with native APIs

	public CANTalonSRX(int deviceNumber, NeutralMode neutralMode, InvertType inverted) {
		super(deviceNumber);
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
