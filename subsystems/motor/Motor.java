// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.subsystems.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.ctre.phoenix.motorcontrol.IMotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A class that wraps around a variable number of MotorController objects to
 * give them Subsystem functionality. Can also modify their speed with a
 * SpeedModifier for things like scaling or brownout protection.
 */
public class Motor extends SubsystemBase implements MotorController {
	protected final MotorController[] motors;
	protected final SpeedModifier speedModifier;
	protected final String name;
	protected boolean isInverted;
	protected double lastSpeed;

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name          The name for the motor
	 * @param isInverted    Inverts the direction of all of the MotorControllers.
	 *                      This does not override the individual inversions of the
	 *                      motors.
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything.
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
	public Motor(String name, boolean isInverted, SpeedModifier speedModifier, MotorController... motors) {
		super();
		setName(name);
		this.name = name;
		this.isInverted = false;
		this.speedModifier = speedModifier;
		this.motors = motors;
		lastSpeed = 0;
		for (MotorController motor : motors) {
			if (motor instanceof IMotorController)
				((IMotorController) motor).enableVoltageCompensation(true);
			motor.set(0); // Start all motors with 0 speed.
		}
		setInverted(isInverted);
		setDefaultCommand(new MotorIdle(this));
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name       The name for the motor
	 * @param isInverted Inverts the direction of all of the MotorControllers. This
	 *                   does not override the individual inversions of the motors.
	 * @param motors     The MotorControllers in this subsystem. Can be a single
	 *                   MotorController or multiple MotorControllers.
	 */
	public Motor(String name, boolean isInverted, MotorController... motors) {
		this(name, isInverted, new IdentityModifier(), motors);
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name          The name for the motor.
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything. Can also regulate set
	 *                      speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
	public Motor(String name, SpeedModifier speedModifier, MotorController... motors) {
		this(name, false, speedModifier, motors);
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name   The name for the motor.
	 * @param motors The MotorControllers in this subsystem. Can be a single
	 *               MotorController or multiple MotorControllers.
	 */
	public Motor(String name, MotorController... motors) {
		this(name, false, new IdentityModifier(), motors);
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param isInverted    Inverts the direction of all of the MotorControllers.
	 *                      This does not override the individual inversions of the
	 *                      motors.
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything. Can also regulate set
	 *                      speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
	public Motor(boolean isInverted, SpeedModifier speedModifier, MotorController... motors) {
		this("Motor", isInverted, speedModifier, motors);
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name          The name for the motor.
	 * @param isInverted    Inverts the direction of all of the MotorControllers.
	 *                      This does not override the individual inversions of the
	 *                      motors.
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything. Can also regulate set
	 *                      speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
	public Motor(boolean isInverted, MotorController... motors) {
		this("Motor", isInverted, motors);
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything. Can also regulate set
	 *                      speed to prevent brownouts (if you use AccelerationCap).
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
	public Motor(SpeedModifier speedModifier, MotorController... motors) {
		this("Motor", speedModifier, motors);
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param motors The MotorControllers in this subsystem. Can be a single
	 *               MotorController or multiple MotorControllers.
	 */
	public Motor(MotorController... motors) {
		this("Motor", motors);
	}

	/**
	 * Disables the motor. This function uses the underlying MotorController's
	 * disable implementation.
	 */
	@Override
	public void disable() {
		for (MotorController motor : motors) {
			motor.disable();
		}
	}

	/**
	 * Stops the motor. This function uses the underlying MotorController's
	 * stopMotor implementation. In theory this should stop the motor without
	 * disabling, but wpilib seems to just call disable under the hood.
	 */
	@Override
	public void stopMotor() {
		for (MotorController motor : motors) {
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
	 * @param speed The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public void set(double speed) {

		LogKitten.v("Motor " + getName() + " @ " + speed);
		double newSpeed = speedModifier.modify(speed);
		lastSpeed = newSpeed;
		for (MotorController motor : motors) {
			motor.set(newSpeed);
		}
	}

	/**
	 * Get whether this entire motor is inverted.
	 *
	 * @return isInverted The state of inversion, true is inverted.
	 */
	@Override
	public boolean getInverted() {
		return isInverted;
	}

	/**
	 * Sets the direction inversion of all motor substituents. This respects the
	 * original inversion state of each MotorController when constructed, and will
	 * only invert MotorControllers if this.getInverted() != the input.
	 *
	 * @param isInverted The state of inversion, true is inverted.
	 */
	@Override
	public void setInverted(boolean isInverted) {
		if (getInverted() != isInverted) {
			for (MotorController motor : motors) {
				motor.setInverted(!motor.getInverted());
			}
		}
		this.isInverted = isInverted;
	}

	protected class UnsynchronizedMotorControllerRuntimeException extends RuntimeException {
		private static final long serialVersionUID = 8688590919561059584L;

		public UnsynchronizedMotorControllerRuntimeException() {
			super(getName() + "'s MotorControllers report different speeds");
		}
	}

	@Deprecated
	protected class StrangeCANMotorControllerModeRuntimeException extends RuntimeException {
		private static final long serialVersionUID = -539917227288371271L;

		public StrangeCANMotorControllerModeRuntimeException() {
			super("One of " + getName()
					+ "'s MotorControllers is a CANMotorController with a non-zero mode. This might mess up it's .get(), so Motor cannot verify safety.");
		}
	}
}
