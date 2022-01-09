package org.usfirst.frc4904.standard.custom.motioncontrollers;

import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class ContinuousServoController extends PWMMotorController {
    public ContinuousServoController(String name, int channel) {
        super(name, channel);
    }
}