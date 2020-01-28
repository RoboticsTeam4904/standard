package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;

public class CANTalonSRX extends WPI_TalonSRX implements SpeedController {
	public CANTalonSRX(int deviceNumber) {
		super(deviceNumber);
	}

	public CANTalonSRX(int deviceNumber, NeutralMode mode) {
		this(deviceNumber);
		setNeutralMode(mode);
	}
}
