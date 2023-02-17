package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class CANTalonFX extends WPI_TalonFX implements TalonMotorController {
	protected static final NeutralMode DEFAULT_NEUTRAL_MODE 	= NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  	= InvertType.FollowMaster;
	protected static final double	   DEFAULT_NEUTRAL_DEADBAND = 0.001;	// 0.1%, the minimum possible value 

	/**
	 * Represents a Falcon motor in code. You probably want NeutralMode.Brake,
	 * InvertType.FollowMaster.
	 *
	 * @param deviceNumber              Usually the CAN ID of the device,
	 *                                  declared in RobotMap
	 * @param inverted                  InvertMode of the motor. If this will be
	 *                                  part of a motor group, consider using
	 *                                  FollowMaster or OpposeMaster, so that
	 *                                  you can invert the entire motor group by
	 *                                  inverting the lead motor. Use None or
	 *                                  InvertMotorOutput for the lead motor.
	 */
	public CANTalonFX(int deviceNumber, InvertType inverted) {
		super(deviceNumber);
		configFactoryDefault();	// use default settings to prevent unexpected behavior, reccommended in examples
		setInverted(inverted);
	}

	/**
	 * Setting to enable brake mode on neutral (when .neutralOutput(),
	 * .disable(), or .stopMotor() is called, or when output percent is within
	 * neutralDeadbandPercent of zero).
	 *
	 * This does not brake the motor. Use .neutralOutput() instead, after
	 * setBrakeOnNeutral.
	 */
	public TalonMotorController setBrakeOnNeutral() {
		setNeutralMode(NeutralMode.Brake);
		return this;
	}
	/**
	 * Setting to enable coast mode on neutral (when .neutralOutput(),
	 * .disable(), or .stopMotor() is called, or when output percent is within
	 * neutralDeadbandPercent of zero).
	 *
	 * This does not coast the motor. Use .neutralOutput() instead, after
	 * setCoastOnNeutral.
	 */
	public TalonMotorController setCoastOnNeutral() {
		setNeutralMode(NeutralMode.Coast);
		return this;
	}
}