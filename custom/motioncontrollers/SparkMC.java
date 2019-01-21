package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class SparkMC extends Spark {
    public final CANEncoder encoder;
    public final CANPIDController motionController;
    public double setpoint;

    public SparkMC(int deviceNumber, CANSparkMaxLowLevel.MotorType type) {
        super(deviceNumber, type);
        this.encoder = getEncoder();
        this.motionController = getPIDController();
    }
    public SparkMC(int deviceNumber){
        this(deviceNumber, CANSparkMaxLowLevel.MotorType.kBrushed);
    }

    public void setPID(double P, double I, double D) {
        motionController.setP(P);
        motionController.setI(I);
        motionController.setD(D);
    }

    public void setPIDF(double P, double I, double D, double F){
        setPID(P, I, D);
        motionController.setFF(F);
    }

    public void setPIDF(double P, double I, double D, double F, double iThreshold){
        setPIDF(P, I, D, F);
        motionController.setIZone(iThreshold);
    }
 
    public void setSetpoint (double setpoint) {
        this.setpoint = setpoint;
    }

    public double getError() {
        return setpoint - encoder.getPosition();
    }
}
