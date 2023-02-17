package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotorController;

public class CustomCANSparkMax extends CANSparkMax implements BrakeableMotorController {
    protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  = InvertType.FollowMaster;
    
    public CustomCANSparkMax(int deviceNumber, MotorType motorType, IdleMode neutralMode, boolean inverted) {
        super(deviceNumber, motorType);
        setIdleMode(neutralMode);
        setInverted(inverted);
    }

    public void setBrakeMode() {
        setIdleMode(IdleMode.kBrake);

    }

	public void setCoastMode() {
        setIdleMode(IdleMode.kCoast);
    }

    @Override
    public void neutralOutput() {
        stopMotor(); 
    }
}
