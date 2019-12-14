package org.usfirst.frc4904.standard.subsystems.motor;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class Motor extends Subsystem implements SpeedController {


    public Motor(String name) {
        super(name);
    }

	@Override
	protected abstract void initDefaultCommand();
	
	/**
	 * Get a set value from a PIDController.
	 *
	 * @param speed
	 *        The speed returned by the PID loop.
	 */
	@Override
	public abstract void pidWrite(double speed);
	
	/**
	 * Disables the motor.
	 * This function uses the underlying SpeedController's disable implementation.
	 */
	@Override
	public abstract void disable();
	
	/**
	 * Stops the motor.
	 * This function uses the underlying SpeedController's stopMotor implementation.
	 * In theory this should stop the motor without disabling,
	 * but wpilib seems to just call disable under the hood.
	 */
	@Override
	public abstract void stopMotor();
	
	/**
	 * Get the most recently set speed.
	 *
	 * @return The most recently set speed between -1.0 and 1.0.
	 */
	@Override
	public abstract double get();
	
	/**
	 * Set the motor speed. Passes through SpeedModifier.
	 *
	 * @param speed
	 *        The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public abstract void set(double speed);
	
	/**
	 * Get whether this entire motor is inverted.
	 *
	 * @return isInverted
	 *         The state of inversion, true is inverted.
	 */
	@Override
	public abstract boolean getInverted();
	
	/**
	 * Sets the direction inversion of all motor substituents.
	 * This respects the original inversion state of each SpeedController when constructed,
	 * and will only invert SpeedControllers if this.getInverted() != the input.
	 *
	 * @param isInverted
	 *        The state of inversion, true is inverted.
	 */
	@Override
	public abstract void setInverted(boolean isInverted);
	
	// protected class UnsynchronizedSpeedControllerRuntimeException extends RuntimeException {
	// 	private static final long serialVersionUID = 8688590919561059584L;
		
	// 	public UnsynchronizedSpeedControllerRuntimeException() {
	// 		super(getName() + "'s SpeedControllers report different speeds");
	// 	}
	// }
	
	// @Deprecated
	// protected class StrangeCANSpeedControllerModeRuntimeException extends RuntimeException {
		
	// }

}   