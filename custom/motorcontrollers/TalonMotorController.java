package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;

/**
 * A base class for CANTalonFX and CANTalonSRX that extends 4904 MotorController
 * but separates Talons from eg. SparkMaxes.
 *
 * May be converted to a general interface for motor controllers in the future,
 * given that SparkMaxes can also do brake mode, follow mode, etc. 
 */
// TODO: call this SmartMotorController
public interface TalonMotorController extends BrakeableMotorController {
    //TODO: add all the things

	// TODO: implement setVoltage with native APIs? or just use voltageComp?

    /**
     * Follow `leader`'s percent output. 
     * 
     * TODO: also add an auxoutput version if SparkMax supports it
     * TODO: use deviceNumber to make this spark/talon agnostic?
     *
     * @param leader    the motor to follow
     */
    public void follow(IMotorController leader);    // are we able to return self to allow builder pattern? prob have to change the method name, else polymorphism breaks bc name collision with the one in base that takes IMotorController 

    // interfaces we use for the talon. See CTRE IMotorControllerEnhanced for the docs.
    public ErrorCode configVoltageCompSaturation(double saturation_voltage, int timeout);
    public void enableVoltageCompensation(boolean enable);
    public int getDeviceID();
    public ErrorCode configNeutralDeadband(double neutral_deadband_percent, int timeout);
    public ErrorCode configForwardLimitSwitchSource(LimitSwitchSource source, LimitSwitchNormal normal_mode, int timeout);
    public ErrorCode configReverseLimitSwitchSource(LimitSwitchSource source, LimitSwitchNormal normal_mode, int timeout);
    public ErrorCode configForwardLimitSwitchSource(RemoteLimitSwitchSource source, LimitSwitchNormal normal_mode, int device_id, int timeout);
    public ErrorCode configReverseLimitSwitchSource(RemoteLimitSwitchSource source, LimitSwitchNormal normal_mode, int device_id, int timeout);
    public void overrideLimitSwitchesEnable(boolean enable);
    public void setNeutralMode(NeutralMode neutral_mode);
}
