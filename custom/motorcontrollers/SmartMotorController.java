
// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.motorcontrollers;

/**
 * Represents a "smart" motor controller, like the TalonFX, TalonSRX, or SparkMax
 * 
 * These should support brake mode, follow mode, limit switches, and various closed-loop control modes.
 */
public interface SmartMotorController {
	public abstract void set(double power);
	public abstract void setPower(double power);
	public abstract void setVoltage(double voltage);

	public abstract SmartMotorController setBrakeOnNeutral();
	public abstract SmartMotorController setCoastOnNeutral();
	public abstract void neutralOutput();
}
