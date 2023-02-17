package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;

public class CustomCANSparkMax extends CANSparkMax implements BrakeableMotorController {
    protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  = InvertType.FollowMaster;
    
    public CustomCANSparkMax(int deviceNumber, MotorType motorType, IdleMode neutralMode, boolean inverted) {
        super(deviceNumber, motorType);
        setIdleMode(neutralMode);
        setInverted(inverted);
    }

    public BrakeableMotorController setBrakeOnNeutral() {
        setIdleMode(IdleMode.kBrake);
        return this;
    }

	public BrakeableMotorController setCoastOnNeutral() {
        setIdleMode(IdleMode.kCoast);
        return this;
    }

    @Override
    public void neutralOutput() {
        stopMotor(); 
    }
}
