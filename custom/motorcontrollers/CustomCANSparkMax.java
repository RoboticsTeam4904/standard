package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;

public class CustomCANSparkMax extends CANSparkMax implements SmartMotorController {
    protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  = InvertType.FollowMaster;
    // protected Double voltage_compensation_max = null;   // remember the configured saturation voltage to conform to the talon api of having separate config() and enable() methods; error if we try to enable without configuring it.
    
    public CustomCANSparkMax(int deviceNumber, MotorType motorType, IdleMode neutralMode) {
        super(deviceNumber, motorType);
        setIdleMode(neutralMode);
        super.restoreFactoryDefaults();
    }

    /**
     * Alias for .set() on power
     * @param power
     */
    public void setPower(double power) { set(power); }

    public SmartMotorController setBrakeOnNeutral() {
        setIdleMode(IdleMode.kBrake);
        return this;
    }

	public SmartMotorController setCoastOnNeutral() {
        setIdleMode(IdleMode.kCoast);
        return this;
    }

    @Override
    public void neutralOutput() {
        stopMotor(); 
    }
}
