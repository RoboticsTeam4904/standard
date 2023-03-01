package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;

public class CustomCANSparkMax extends CANSparkMax implements TalonMotorController {
    protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  = InvertType.FollowMaster;
    protected Double saturation_voltage = null;   // remember the configured saturation voltage to conform to the talon api of having separate config() and enable() methods; error if we try to enable without configuring it.
    
    public CustomCANSparkMax(int deviceNumber, MotorType motorType, IdleMode neutralMode) {
        super(deviceNumber, motorType);
        setIdleMode(neutralMode);
        super.restoreFactoryDefaults();
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

    /// talon compatibilty interface, so that you can shove CustomCANSparkMaxes into the motorsubsystem classes
    // TODO: return the right error codes
    public ErrorCode configVoltageCompSaturation(double saturation_voltage, int timeout_ms__unused) {
        // we need to store the saturation voltage ourselves in order to mimick the talon interface of configuring seprately from enabling
        this.saturation_voltage = saturation_voltage;
        return ErrorCode.OK;
    }
    public void enableVoltageCompensation(boolean enable) {
        if (enable) {
            if (this.saturation_voltage == null)    // fail if we have yet to configure the voltage comp
                throw new NullPointerException("Tried to enable voltage compensation on SparkMax via the Talon compatibility interface without first configuring it.");
            this.enableVoltageCompensation(this.saturation_voltage);
        } else {
            this.disableVoltageCompensation();
        }
    }

    public void setNeutralMode(NeutralMode talon_neutral_mode) {
        var idle_mode = switch (talon_neutral_mode) {
            case NeutralMode.Brake -> IdleMode.kBrake;
            case NeutralMode.Coast -> IdleMode.kCoast;
            default -> throw new IllegalArgumentException("CANSparkMax neutralMode must be set to Brake or Coast");
        }
        this.setIdleMode(IdleMode.kBrake);
    }
}
