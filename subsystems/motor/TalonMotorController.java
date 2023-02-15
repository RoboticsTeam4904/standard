package org.usfirst.frc4904.standard.subsystems.motor;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

/**
 * A base class for CANTalonFX and CANTalonSRX that extends 4904 MotorController
 * but separates Talons from eg. SparkMaxes.
 *
 * May be converted to a general interface for motor controllers in the future,
 * given that SparkMaxes can also do brake mode, follow mode, etc. 
 */
public interface TalonMotorController extends BrakeableMotorController, IMotorControllerEnhanced {
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
}
