package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.ReverseLimitValue;
import com.ctre.phoenix6.hardware.TalonFX;

public class CANTalonFX extends TalonFX {
	protected static final NeutralModeValue DEFAULT_NEUTRAL_MODE 	= NeutralModeValue.Coast;
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
	public CANTalonFX(int deviceNumber) {
		super(deviceNumber);
	}

    /**
     * Alias for .set() on power
     * @param power
     */
    public void setPower(double power) { set(power); }

	/**
	 * Setting to enable brake mode on neutral (when .neutralOutput(),
	 * .disable(), or .stopMotor() is called, or when output percent is within
	 * neutralDeadbandPercent of zero).
	 *
	 * This does not brake the motor. Use .neutralOutput() instead, after
	 * setBrakeOnNeutral.
	 */
	public CANTalonFX setBrakeOnNeutral() {
		setNeutralMode(NeutralModeValue.Brake);
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
	public CANTalonFX setCoastOnNeutral() {
		setNeutralMode(NeutralModeValue.Coast);
		return this;
	}
	public ForwardLimitValue isFwdLimitSwitchPressed() throws IllegalAccessException {
		// OPTIM: this should probably support normally closed limit switches too... right now only supports normally open
		return getForwardLimit().getValue();
	}
	public ReverseLimitValue isRevLimitSwitchPressed() throws IllegalAccessException {
		// OPTIM: this should probably support normally closed limit switches too... right now only supports normally open
		return getReverseLimit().getValue();
	}
}