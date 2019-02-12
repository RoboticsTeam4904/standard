package org.usfirst.frc4904.standard.custom.motioncontrollers;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * CANSparkMax MotorController
 */
public class Spark extends CANSparkMax implements SpeedController {
    /**
     * CANSparkMax MotorController
     * 
     * @param deviceNumber
     *                     port number
     * @param type
     *                     Type of motor: brushed, brushless
     */
    public Spark(int deviceNumber, CANSparkMaxLowLevel.MotorType type) {
        super(deviceNumber, type);
    }

    /**
     * CANSparkMax MotorController
     * 
     * @param deviceNumber
     *                     port number
     */
    public Spark(int deviceNumber) {
        this(deviceNumber, CANSparkMaxLowLevel.MotorType.kBrushed);
    }
}