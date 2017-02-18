package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
 */
public class Motor extends Subsystem implements SpeedController {
	protected final SpeedController[] motors;
	protected final SpeedModifier speedModifier;
	protected boolean isInverted;
	protected double lastSpeed;

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name
	 *        The name for the motor
	 * @param isInverted
	 *        Inverts the direction of all of the SpeedControllers.
	 *        This does not override the individual inversions of the motors.
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(String name, boolean isInverted, SpeedModifier speedModifier, SpeedController... motors) {
		super(name);
		this.isInverted = false;
		this.speedModifier = speedModifier;
		this.motors = motors;
		lastSpeed = 0;
		for (SpeedController motor : motors) {
			if (motor instanceof CANSpeedController && ((CANSpeedController) motor).getControlMode().getValue() != 0) { // 0 is hopefully normal motor controller mode (Like CANTalon's PercentVBus mode)
				throw new StrangeCANSpeedControllerModeRuntimeException(this);
			}
			motor.set(0); // Start all motors with 0 speed.
		}
		setInverted(isInverted);
	}

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name
	 *        The name for the motor
	 * @param isInverted
	 *        Inverts the direction of all of the SpeedControllers.
	 *        This does not override the individual inversions of the motors.
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(String name, boolean isInverted, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), motors);
	}

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name
	 *        The name for the motor.
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 *        Can also regulate set speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(String name, SpeedModifier speedModifier, SpeedController... motors) {
		this(name, false, speedModifier, motors);
	}

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name
	 *        The name for the motor.
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(String name, SpeedController... motors) {
		this(name, false, new IdentityModifier(), motors);
	}

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param isInverted
	 *        Inverts the direction of all of the SpeedControllers.
	 *        This does not override the individual inversions of the motors.
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 *        Can also regulate set speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(boolean isInverted, SpeedModifier speedModifier, SpeedController... motors) {
		this("Motor", isInverted, speedModifier, motors);
	}

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name
	 *        The name for the motor.
	 * @param isInverted
	 *        Inverts the direction of all of the SpeedControllers.
	 *        This does not override the individual inversions of the motors.
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 *        Can also regulate set speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(boolean isInverted, SpeedController... motors) {
		this("Motor", isInverted, motors);
	}

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 *        Can also regulate set speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(SpeedModifier speedModifier, SpeedController... motors) {
		this("Motor", speedModifier, motors);
	}

	/**
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param motors
	 *        The SpeedControllers in this subsystem.
	 *        Can be a single SpeedController or multiple SpeedControllers.
	 */
	public Motor(SpeedController... motors) {
		this("Motor", motors);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}

	/**
	 * Get a set value from a PIDController.
	 *
	 * @param speed
	 *        The speed returned by the PID loop.
	 */
	@Override
	public void pidWrite(double speed) {
		double newSpeed = speedModifier.modify(speed);
		lastSpeed = newSpeed;
		for (SpeedController motor : motors) {
			motor.pidWrite(newSpeed);
		}
	}

	/**
	 * Disables the motor.
	 * This function uses the underlying SpeedController's disable implementation.
	 */
	@Override
	public void disable() {
		for (SpeedController motor : motors) {
			motor.disable();
		}
	}

	/**
	 * Stops the motor.
	 * This function uses the underlying SpeedController's stopMotor implementation.
	 * In theory this should stop the motor without disabling,
	 * but wpilib seems to just call disable under the hood.
	 */
	@Override
	public void stopMotor() {
		for (SpeedController motor : motors) {
			motor.stopMotor();
		}
	}

	/**
	 * Get the most recently set speed.
	 *
	 * @return The most recently set speed between -1.0 and 1.0.
	 */
	@Override
	public double get() {
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
		LogKitten.v("Motor " + getName() + " @ " + speed);
		double newSpeed = speedModifier.modify(speed);
		lastSpeed = newSpeed;
		for (SpeedController motor : motors) {
			motor.set(newSpeed);
		}
	}

	/**
	 * Get whether this entire motor is inverted.
	 *
	 * @return isInverted
	 *         The state of inversion, true is inverted.
	 */
	@Override
	public boolean getInverted() {
		return isInverted;
	}

	/**
	 * Sets the direction inversion of all motor substituents.
	 * This respects the original inversion state of each SpeedController when constructed,
	 * and will only invert SpeedControllers if this.getInverted() != the input.
	 *
	 * @param isInverted
	 *        The state of inversion, true is inverted.
	 */
	@Override
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
			super("One of " + motor.getName()
				+ "'s SpeedControllers is a CANSpeedController with a non-zero mode. This might mess up it's .get(), so Motor cannot verify safety.");
		}
	}
}
