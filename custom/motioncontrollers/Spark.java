package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotor;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * CANSparkMax MotorController
 */
public class Spark extends CANSparkMax implements SpeedController, BrakeableMotor {
    /**
     * CANSparkMax MotorController
     * 
     * @param deviceNumber Port number
     * @param type         Type of motor: brushed, brushless
     * @param mode         Idle Mode: Brake, Coast
     */
    public Spark(int deviceNumber, CANSparkMaxLowLevel.MotorType type, IdleMode mode) {
        super(deviceNumber, type);
        setIdleMode(mode);
    }

    /**
     * CANSparkMax MotorController
     * 
     * @param deviceNumber port number
     * @param type         Type of motor: brushed, brushless
     */
    public Spark(int deviceNumber, CANSparkMaxLowLevel.MotorType type) {
        this(deviceNumber, type, IdleMode.kBrake);
    }

    /**
     * CANSparkMax MotorController
     * 
     * @param deviceNumber port number
     */
    public Spark(int deviceNumber) {
        this(deviceNumber, CANSparkMaxLowLevel.MotorType.kBrushed);
    }

    /**
     * Sets IdleMode to Brake. Motor will default to break mode when speed set to
     * 0.0
     */
    public void setBrakeMode() {
        setIdleMode(IdleMode.kBrake);
    }

    /**
     * Sets IdleMode to Coast. Motor will default to coast mode when speed set to
     * 0.0
     */
    public void setCoastMode() {
        setIdleMode(IdleMode.kCoast);
    }

    // TODO: Need to test
    /**
     * Sets speed to 0.0 to set motor to the set IdleMode
     */
    public void neutralOutput() {
        set(0.0);
    }
}