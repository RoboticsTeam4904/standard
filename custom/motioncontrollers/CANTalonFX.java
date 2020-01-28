package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.SpeedController;

public class CANTalonFX extends WPI_TalonFX implements SpeedController {
	public CANTalonFX(int deviceNumber) {
		super(deviceNumber);
	}

	public CANTalonFX(int deviceNumber, NeutralMode mode) {
		this(deviceNumber);
		setNeutralMode(mode);
	}
}