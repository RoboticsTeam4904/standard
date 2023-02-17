
// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.subsystems.motor;

/**
 * An abstract motor interface that replaces the WPILib motor interface 
 * - to force custom implementation of setVoltage (WPILib's uses RobotController.getBatteryVoltage() which is slow, cite @zbuster05)
 */
public interface MotorController extends edu.wpi.first.wpilibj.motorcontrol.MotorController {
	/**
	 * Implementations should implement this using a better voltage reading than
	 * RobotController.getBatteryVoltage(), preferably a native interface.
	 *
	 * NOTE FROM BASE CLASS: This function *must* be called regularly in order
	 * for voltage compensation to work properly - unlike the ordinary set
	 * function, it is not "set it and forget it."
	 */
	@Override
	public abstract void setVoltage(double voltage);
}
