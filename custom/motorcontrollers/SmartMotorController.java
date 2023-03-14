
// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.motorcontrollers;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Represents a "smart" motor controller, like the TalonFX, TalonSRX, or SparkMax
 * 
 * These should support brake mode, follow mode, limit switches, and various closed-loop control modes.
 */
public interface SmartMotorController extends MotorController {
	public abstract void setPower(double power);

	public abstract boolean isFwdLimitSwitchPressed() throws IllegalAccessException;	// limit switch methods are here because: talonfx and talonsrx has different types for sensorcollection and I didn't want to make TalonMotorSubsystem generic -> require a type parameter; sparkmax also has it. can't be on subsystem level (because talonMotorSubsystem doesn't differentiate b/w talonfx and talonsrx) so it has to be here.
	public abstract boolean isRevLimitSwitchPressed() throws IllegalAccessException;	// needs to throw an exception so that the downstream CustomCANSparkMax can throw an exception if we try to read from limit switches before they are declared as normallyOpen or normallyClosed

	public abstract SmartMotorController setBrakeOnNeutral();
	public abstract SmartMotorController setCoastOnNeutral();
	public abstract void neutralOutput();
}
