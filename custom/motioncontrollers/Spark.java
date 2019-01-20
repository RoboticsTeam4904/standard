package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SpeedController;

public class Spark extends CANSparkMax implements SpeedController {
    public final CANEncoder encoder;
    public final CANPIDController motioncontroller;
    public Spark(int deviceNumber, CANSparkMaxLowLevel.MotorType type) {
        super(deviceNumber, type);
        this.encoder = getEncoder();
        this.motioncontroller = getPIDController();

    }
    public Spark(int deviceNumber) {
        this(deviceNumber, CANSparkMaxLowLevel.MotorType.kBrushed);
    }
}