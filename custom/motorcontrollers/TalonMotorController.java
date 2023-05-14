package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

/**
 * A base class for CANTalonFX and CANTalonSRX that extends 4904
 * SmartMotorController, and differentiates Talons from eg. SparkMaxes.
 * 
 * See CANTalonFX or CANTalonSRX for implementation details.
 */
public interface TalonMotorController extends SmartMotorController, IMotorControllerEnhanced {}
