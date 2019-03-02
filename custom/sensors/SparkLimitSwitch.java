package org.usfirst.frc4904.standard.custom.sensors;


import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANError;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import org.usfirst.frc4904.standard.custom.CANMessageUnavailableException;
import org.usfirst.frc4904.standard.custom.motioncontrollers.Spark;
import edu.wpi.first.hal.can.CANMessageNotFoundException;
import edu.wpi.first.wpilibj.buttons.Button;

public class SparkLimitSwitch extends Button implements CustomButton {
    protected final CANDigitalInput limitSwitch;

    public SparkLimitSwitch(Spark spark, LimitSwitch direction, LimitSwitchPolarity polarity) {
        limitSwitch = new CANDigitalInput(spark, direction, polarity);
        CANError status = limitSwitch.enableLimitSwitch(true);
        if (status != CANError.kOK) {
            throw new CANMessageNotFoundException("Limit Switch Status Not Ok");
        }
    }

    public SparkLimitSwitch(Spark spark, LimitSwitch direction) {
        this(spark, direction, LimitSwitchPolarity.kNormallyClosed);
    }

    public SparkLimitSwitch(Spark spark, LimitSwitchPolarity polarity) {
        this(spark, LimitSwitch.kForward, polarity);
    }

    public SparkLimitSwitch(Spark spark) {
        this(spark, LimitSwitchPolarity.kNormallyClosed);
    }

    public void enable() throws CANMessageUnavailableException {
        CANError status = limitSwitch.enableLimitSwitch(true);
        if (status != CANError.kOK) {
            throw new CANMessageUnavailableException("Limit Switch Status Not Ok");
        }
    }

    public void disable() throws CANMessageUnavailableException {
        CANError status = limitSwitch.enableLimitSwitch(false);
        if (status != CANError.kOK) {
            throw new CANMessageUnavailableException("Limit Switch Status Not Ok");
        }
    }

    @Override
    public boolean get() {
        return !limitSwitch.get();
    }
}