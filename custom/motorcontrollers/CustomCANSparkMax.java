package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;


public class CustomCANSparkMax extends SparkMax implements SmartMotorController {
    SparkBaseConfig brakeConfig = new SparkMaxConfig().idleMode(IdleMode.kBrake);
    SparkBaseConfig coastConfig = new SparkMaxConfig().idleMode(IdleMode.kCoast);

    // protected Double voltage_compensation_max = null;   // remember the configured saturation voltage to conform to the talon api of having separate config() and enable() methods; error if we try to enable without configuring it.
    protected boolean limitSwitch;

    public CustomCANSparkMax(int deviceNumber, MotorType motorType, boolean inverted, boolean limitSwitch) {
        super(deviceNumber, motorType);

        configure( //configs inversion and should reset to defaults on init
            new SparkMaxConfig().inverted(inverted),
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
        this.limitSwitch = limitSwitch;  // have to store it bc we need to error if we try to read switch pressed state without knowing switch type
    }

    public CustomCANSparkMax(int deviceNumber, MotorType motorType, boolean inverted) {
        this(deviceNumber, motorType, inverted, false);
    }

    /**
     * Alias for .set() on power
     * @param power
     */
    public void setPower(double power) { set(power); }

    public SmartMotorController setBrakeOnNeutral() {
        configure(
            brakeConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        return this;
    }

    public SmartMotorController setCoastOnNeutral() {
        configure(
            coastConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        return this;
    }

    @Override
    public void neutralOutput() {
        stopMotor();
    }

    @Override
    public boolean isFwdLimitSwitchPressed() throws IllegalAccessException {
        if (!limitSwitch) throw new IllegalAccessException("Cannot read limit switch state when CustomCANSparkMax was constructed without limit switch type!");
        return getForwardLimitSwitch().isPressed();
    }

    @Override
    public boolean isRevLimitSwitchPressed() throws IllegalAccessException {
        if (!limitSwitch) throw new IllegalAccessException("Cannot read limit switch state when CustomCANSparkMax was constructed without limit switch type!");
        return getReverseLimitSwitch().isPressed();
    }
}
