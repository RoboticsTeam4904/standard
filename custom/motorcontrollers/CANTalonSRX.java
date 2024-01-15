// TODO: fix it
// // THIS FILE IS TESTED post wpilibj2
// 
// package org.usfirst.frc4904.standard.custom.motorcontrollers;

// import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.NeutralModeValue;
// import com.ctre.phoenix6.motorcontrol.can.WPI_TalonSRX;
// public class CANTalonSRX extends WPI_TalonSRX implements TalonMotorController {
// 	protected static final NeutralMode DEFAULT_NEUTRAL_MODE 	= NeutralMode.Coast;
// 	protected static final InvertType  DEFAULT_INVERT_TYPE  	= InvertType.FollowMaster;
// 	protected static final double	   DEFAULT_NEUTRAL_DEADBAND = 0.001;	// 0.1%, the minimum possible value 

// 	/**
// 	 * Represents a TalonSRX controller (eg. attached to a 775) in code. You
// 	 * probably want NeutralMode.Brake, InvertType.FollowMaster.
// 	 *
// 	 * @param deviceNumber              Usually the CAN ID of the device,
// 	 * declared in RobotMap
// 	 * @param inverted                  InvertMode of the motor. If this will be
// 	 *                                  part of a motor group, consider using
// 	 *                                  FollowMaster or OpposeMaster, so that you
// 	 *                                  can invert the entire motor group by
// 	 *                                  inverting the lead motor. Use None or
// 	 *                                  InvertMotorOutput for the lead motor.
// 	 */
// 	public CANTalonSRX(int deviceNumber, InvertType inverted) {
// 		super(deviceNumber);
// 		configFactoryDefault();	// use default settings to prevent unexpected behavior, reccommended in examples
// 		setInverted(inverted);
// 	}

//     /**
//      * Alias for .set() on power
//      * @param power
//      */
//     public void setPower(double power) { set(power); }

// 	/**
// 	 * Setting to enable brake mode on neutral (when .neutralOutput(),
// 	 * .disable(), or .stopMotor() is called, or when output percent is within
// 	 * neutralDeadbandPercent of zero).
// 	 *
// 	 * This does not brake the motor. Use .neutralOutput() instead, after
// 	 * setBrakeOnNeutral.
// 	 */
// 	public TalonMotorController setBrakeOnNeutral() {
// 		setNeutralMode(NeutralMode.Brake);
// 		return this;
// 	}
// 	/**
// 	 * Setting to enable coast mode on neutral (when .neutralOutput(),
// 	 * .disable(), or .stopMotor() is called, or when output percent is within
// 	 * neutralDeadbandPercent of zero).
// 	 *
// 	 * This does not coast the motor. Use .neutralOutput() instead, after
// 	 * setCoastOnNeutral.
// 	 */
// 	public TalonMotorController setCoastOnNeutral() {
// 		setNeutralMode(NeutralMode.Coast);
// 		return this;
// 	}

// 	@Override
// 	public boolean isFwdLimitSwitchPressed() throws IllegalAccessException {
// 		// OPTIM: this should probably support normally closed limit switches too... right now only supports normally open
// 		return getSensorCollection().isFwdLimitSwitchClosed();
// 	}
// 	@Override
// 	public boolean isRevLimitSwitchPressed() throws IllegalAccessException {
// 		// OPTIM: this should probably support normally closed limit switches too... right now only supports normally open
// 		return getSensorCollection().isRevLimitSwitchClosed();
// 	}
// }
