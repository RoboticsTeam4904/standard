package org.usfirst.frc4904.standard.custom.motorcontrollers;

import com.revrobotics.CANSparkMax;

<<<<<<< HEAD
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;

public class CustomCANSparkMax extends CANSparkMax implements BrakeableMotorController {
    protected static final NeutralMode DEFAULT_NEUTRAL_MODE = NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  = InvertType.FollowMaster;
    protected Double saturation_voltage = null;   // remember the configured saturation voltage to conform to the talon api of having separate config() and enable() methods; error if we try to enable without configuring it.
=======
public class CustomCANSparkMax extends CANSparkMax implements SmartMotorController {
    // protected Double voltage_compensation_max = null;   // remember the configured saturation voltage to conform to the talon api of having separate config() and enable() methods; error if we try to enable without configuring it.
>>>>>>> 3cd05009846892b079be825789d18e63437b3f73
    
    public CustomCANSparkMax(int deviceNumber, MotorType motorType, boolean inverted) {
        super(deviceNumber, motorType);
        setInverted(inverted);
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
