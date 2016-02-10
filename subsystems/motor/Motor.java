package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
 * Keeps the substituent SpeedController objects in sync.
 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
 */
public class Motor extends Subsystem implements SpeedController {
	protected final SpeedController[] motors;
	protected final SpeedModifier speedModifier;
	protected boolean isInverted;
	protected double lastSpeed;
	
	public Motor(String name, boolean isInverted, SpeedModifier speedModifier, SpeedController... motors) {
		super(name);
		this.isInverted = isInverted;
		this.speedModifier = speedModifier;
		this.motors = motors;
		this.lastSpeed = 0;
		for (SpeedController motor : motors) {
			if (motor instanceof CANSpeedController && ((CANSpeedController) motor).getControlMode().getValue() != 0) { // 0 is hopefully normal motor controller mode (Like CANTalon's PercentVBus mode)
				throw new StrangeCANSpeedControllerModeRuntimeException(this);
			}
		}
		setInverted(isInverted);
	}
	
	public Motor(String name, boolean isInverted, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), motors);
	}
	
	public Motor(String name, SpeedModifier slopeController, SpeedController... motors) {
		this(name, false, slopeController, motors);
	}
	
	public Motor(String name, SpeedController... motors) {
		this(name, false, new IdentityModifier(), motors);
	}
	
	public Motor(boolean isInverted, SpeedModifier speedModifier, SpeedController... motors) {
		this("Motor", isInverted, speedModifier, motors);
	}
	
	public Motor(boolean isInverted, SpeedController... motors) {
		this("Motor", isInverted, motors);
	}
	
	public Motor(SpeedModifier slopeController, SpeedController... motors) {
		this("Motor", slopeController, motors);
	}
	
	public Motor(SpeedController... motors) {
		this("Motor", motors);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}
	
	/**
	 * Ensure that all SpeedController objects report the same speed from motor.get().
	 * Otherwise, throw an UnsynchronizedSpeedControllerRuntimeException.
	 * This will fail if you mix CANTalons in weird modes with normal motor controllers, so don't.
	 */
	protected void ensureSafety() {
		double value = motors[0].get();
		for (SpeedController motor : motors) {
			if (value != motor.get()) {
				disable();
				throw new UnsynchronizedSpeedControllerRuntimeException(this);
			}
		}
	}
	
	@Override
	public void pidWrite(double speed) {
		ensureSafety();
		lastSpeed = speed;
		double newSpeed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.pidWrite(newSpeed);
		}
	}
	
	@Override
	public void disable() {
		for (SpeedController motor : motors) {
			motor.disable();
		}
	}
	
	/**
	 * Get the most recently set speed.
	 *
	 * @return The most recently set speed between -1.0 and 1.0.
	 */
	@Override
	public double get() {
		ensureSafety();
		return lastSpeed;
	}
	
	/**
	 * Set the motor speed. Passes through SpeedModifier.
	 *
	 * @param speed
	 *        The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public void set(double speed) {
		ensureSafety();
		lastSpeed = speed;
		double newSpeed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.set(newSpeed);
		}
	}
	
	/**
	 * Set the motor speed. Passes through SpeedModifier.
	 *
	 * @deprecated For compatibility with CANJaguar. Use set(double speed)
	 * 			
	 * @param speed
	 *        The speed to set. Value should be between -1.0 and 1.0.
	 * @param syncGroup
	 *        On CANJaguar, the update group to add this Set() to, pending
	 *        UpdateSyncGroup(). If 0, update immediately.
	 */
	@Deprecated
	@Override
	public void set(double speed, byte syncGroup) {
		ensureSafety();
		lastSpeed = speed;
		double newSpeed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.set(newSpeed, syncGroup);
		}
	}
	
	/**
	 * Get whether this entire motor is inverted.
	 *
	 * @return isInverted
	 *         The state of inversion, true is inverted.
	 * 		
	 */
	public boolean getInverted() {
		return isInverted;
	}
	
	/**
	 * Sets the direction inversion of all motor substituents.
	 * This respects the original inversion state of each SpeedController when constructed, and will only
	 * invert SpeedControllers if this.getInverted() != the input.
	 *
	 * @param isInverted
	 *        The state of inversion, true is inverted.
	 */
	public void setInverted(boolean isInverted) {
		if (getInverted() != isInverted) {
			for (SpeedController motor : motors) {
				motor.setInverted(!motor.getInverted());
			}
		}
		this.isInverted = isInverted;
	}
	
	protected class UnsynchronizedSpeedControllerRuntimeException extends RuntimeException {
		private static final long serialVersionUID = 8688590919561059584L;
		
		public UnsynchronizedSpeedControllerRuntimeException(Motor motor) {
			super(motor.getName() + "'s SpeedControllers report different speeds");
		}
	}
	
	protected class StrangeCANSpeedControllerModeRuntimeException extends RuntimeException {
		private static final long serialVersionUID = -539917227288371271L;
		
		public StrangeCANSpeedControllerModeRuntimeException(Motor motor) {
			super("One of " + motor.getName() + "'s SpeedControllers is a CANSpeedController with a non-zero mode. This might mess up it's .get(), so Motor cannot verify safety.");
		}
	}
}
