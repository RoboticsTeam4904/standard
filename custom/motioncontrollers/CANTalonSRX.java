// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotor;

public class CANTalonSRX extends WPI_TalonSRX implements BrakeableMotor {
	protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;

	public CANTalonSRX(int deviceNumber, NeutralMode mode) {
		super(deviceNumber);
		setNeutralMode(mode);
	}

	public CANTalonSRX(int deviceNumber) {
		this(deviceNumber, DEFAULT_NEUTRAL_MODE);
	}

	public void setCoastMode() {
		setNeutralMode(NeutralMode.Coast);
	}

	public void setBrakeMode() {
		setNeutralMode(NeutralMode.Brake);
	}
}
