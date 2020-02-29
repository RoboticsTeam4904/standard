package org.usfirst.frc4904.standard.custom.motioncontrollers;

import edu.wpi.first.wpilibj.PWMSpeedController;

public class ContinuousServoController extends PWMSpeedController {
    public ContinuousServoController(int channel) {
        super(channel);
    }
}