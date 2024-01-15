package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkLimitSwitch.Type;


public class CustomCANSparkMax extends CANSparkMax implements SmartMotorController {
    // protected Double voltage_compensation_max = null;   // remember the configured saturation voltage to conform to the talon api of having separate config() and enable() methods; error if we try to enable without configuring it.
    protected Type switchType;

    public CustomCANSparkMax(int deviceNumber, MotorType motorType, boolean inverted, Type limitSwitchType) {
        super(deviceNumber, motorType);
        setInverted(inverted);
        this.switchType = limitSwitchType;  // have to store it bc we need to error if we try to read switch pressed state without knowing switch type
        super.restoreFactoryDefaults();
    }
    
    public CustomCANSparkMax(int deviceNumber, MotorType motorType, boolean inverted) {
        this(deviceNumber, motorType, inverted, null);
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

    @Override
    public boolean isFwdLimitSwitchPressed() throws IllegalAccessException {
        if (switchType == null) throw new IllegalAccessException("Cannot read limit switch state when CustomCANSparkMax was constructed without limit switch type!");
        return getForwardLimitSwitch(switchType).isPressed();
    }

    @Override
    public boolean isRevLimitSwitchPressed() throws IllegalAccessException {
        if (switchType == null) throw new IllegalAccessException("Cannot read limit switch state when CustomCANSparkMax was constructed without limit switch type!");
        return getReverseLimitSwitch(switchType).isPressed();
    }
}
