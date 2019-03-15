package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
 * but isn't actually connected to a motor.
 */
public class FakeMotor extends Motor {
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
	 *
	 * @param name
	 *        The name for the motor
	 * @param isInverted
	 *        Inverts the direction of all of the SpeedControllers.
	 *        This does not override the individual inversions of the motors.
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 */
	public FakeMotor(String name, boolean isInverted, SpeedModifier speedModifier, SpeedController... motors) {
		super(name, isInverted, speedModifier, new SpeedController[0]);
	}
	
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
	 *
	 * @param name
	 *        The name for the motor
	 * @param isInverted
	 *        Inverts the direction of all of the SpeedControllers.
	 *        This does not override the individual inversions of the motors.
	 */
	public FakeMotor(String name, boolean isInverted) {
		this(name, isInverted, new IdentityModifier());
	}
	
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
	 *
	 * @param name
	 *        The name for the motor.
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 *        Can also regulate set speed to prevent brownouts (if you use AccelerationCap).
	 */
	public FakeMotor(String name, SpeedModifier speedModifier) {
		this(name, false, speedModifier);
	}
	
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
	 *
	 * @param name
	 *        The name for the motor.
	 */
	public FakeMotor(String name) {
		this(name, false, new IdentityModifier());
	}
	
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
	 *
	 * @param isInverted
	 *        Inverts the direction of all of the SpeedControllers.
	 *        This does not override the individual inversions of the motors.
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 *        Can also regulate set speed to prevent brownouts (if you use AccelerationCap).
	 */
	public FakeMotor(boolean isInverted, SpeedModifier speedModifier) {
		this("FakeMotor", isInverted, speedModifier);
	}
	
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
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
	 */
	public FakeMotor(boolean isInverted) {
		this("FakeMotor", isInverted);
	}
	
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
	 *
	 * @param speedModifier
	 *        A SpeedModifier changes the input to every motor based on some factor.
	 *        The default is an IdentityModifier, which does not affect anything.
	 *        Can also regulate set speed to prevent brownouts (if you use AccelerationCap).
	 */
	public FakeMotor(SpeedModifier speedModifier) {
		this("FakeMotor", speedModifier);
	}
	
	/**
	 * A class that spoofs a Motor and can be used for all motor commands, constructors, and methods,
	 * but isn't actually connected to a motor.
	 */
	public FakeMotor() {
		this("FakeMotor");
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
		// for (SpeedController motor : motors) {
		// 	motor.pidWrite(newSpeed);
		// }
	}
	
	/**
	 * Disables the motor.
	 * This function uses the underlying SpeedController's disable implementation.
	 */
	@Override
	public void disable() {
		// for (SpeedController motor : motors) {
		// 	motor.disable();
		// }
	}
	
	/**
	 * Stops the motor.
	 * This function uses the underlying SpeedController's stopMotor implementation.
	 * In theory this should stop the motor without disabling,
	 * but wpilib seems to just call disable under the hood.
	 */
	@Override
	public void stopMotor() {
		// for (SpeedController motor : motors) {
		// 	motor.stopMotor();
		// }
	}
	
	/**
	 * Set the motor speed. Passes through SpeedModifier.
	 *
	 * @param speed
	 *        The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public void set(double speed) {
		LogKitten.v("FakeMotor " + getName() + " @ " + speed);
		double newSpeed = speedModifier.modify(speed);
		lastSpeed = newSpeed;
		// for (SpeedController motor : motors) {
		// 	motor.set(newSpeed);
		// }
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
			// for (SpeedController motor : motors) {
			// 	motor.setInverted(!motor.getInverted());
			// }
		}
		this.isInverted = isInverted;
	}
}
