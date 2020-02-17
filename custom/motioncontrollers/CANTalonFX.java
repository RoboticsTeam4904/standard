package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotor;

import edu.wpi.first.wpilibj.SpeedController;

public class CANTalonFX extends WPI_TalonFX implements SpeedController, BrakeableMotor {
	public CANTalonFX(int deviceNumber) {
		super(deviceNumber);
	}

	public CANTalonFX(int deviceNumber, NeutralMode mode) {
		this(deviceNumber);
		setNeutralMode(mode);
	}

	public void setCoastMode() {
		setNeutralMode(NeutralMode.Coast);
	}

	public void setBrakeMode() {
		setNeutralMode(NeutralMode.Brake);
	}
}