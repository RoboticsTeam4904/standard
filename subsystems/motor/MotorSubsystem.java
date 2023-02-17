package org.usfirst.frc4904.standard.subsystems.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.motorcontrollers.MotorController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Wraps MotorControllers to represent as a subsystem. Should be used as the
 * atomic, primary motor class for subsystem compositions.
 *
 * Does not include inversion logic, instead, MotorControllers should be
 * inverted before constructing this class. Please use 4904custom
 * MotorControllers rather than wpilibj2 MotorControllers.
 *
 * Replaces Motor.java in pre-2023 standard, except without CTRE voltage comp by
 * default and without inversion logic. 
 */
public class MotorSubsystem<MC extends MotorController> extends SubsystemBase {
    protected final MC[] motors;
	protected final SpeedModifier speedModifier;    // NOTE: maybe change to be called PowerModifier
	protected final String name;

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their power with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name          The name for the motor
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything.
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
	public MotorSubsystem(String name, SpeedModifier speedModifier, MC... motors) {
		super();
		setName(name);
		this.name = name;
		this.speedModifier = speedModifier;
		this.motors = motors;
		for (MotorController motor : motors) {
			motor.set(0);
		}
		setDefaultCommand(this.c_idle());
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param name       The name for the motor
	 * @param motors     The MotorControllers in this subsystem. Can be a single
	 *                   MotorController or multiple MotorControllers.
	 */
	public MotorSubsystem(String name, MC... motors) {
		this(name, new IdentityModifier(), motors);
	}

	/**
	 * A class that wraps around a variable number of MotorController objects to
	 * give them Subsystem functionality. Can also modify their speed with a
	 * SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything.
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
    public MotorSubsystem(SpeedModifier speedModifier, MC... motors) {
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
	public MotorSubsystem(MC... motors) {
		this("Motor", motors);
	}

    /// METHODS
	/**
	 * Disables the motor with underlying .disable()
	 */
	public void disable() {
		for (MotorController motor : motors) {
			motor.disable();
		}
	}

	/**
	 * Stops the motor with underlying stopMotor()
     *
	 * In theory this should stop the motor without disabling, but wpilib seems
	 * to just call disable under the hood.
	 */
	public void stopMotor() {
		for (MotorController motor : motors) {
			motor.stopMotor();
		}
	}

	// if you implement a .get() to get the power, make sure you update it in setVoltage() too (eg. with voltage/RobotController.getBatteryVoltage())

	/**
	 * Set the motor power. Passes through SpeedModifier.
	 *
	 * @param power The power to set. Value should be between -1.0 and 1.0.
	 */
	public void set(double power) {
		LogKitten.v("Motor " + getName() + " @ " + power);
		double newPower = speedModifier.modify(power);
		for (MotorController motor : motors) {
			motor.set(newPower);
		}
	}
    
    /**
     * Identical to set(). Set motor power, passing through SpeedModifier.
	 *
	 * @param power The power to set. Value should be between -1.0 and 1.0.
     */
    public void setPower(double power) {
        set(power);
    }

	/**
	 * Set the motor voltages with the underlying setVoltage(). 
	 *
	 * @param voltage The voltage to set. Future calls to get() will return the
	 *                ratio of this voltage to the current battery voltage.
     * 
	 * NOTE FROM BASE CLASS: This function *must* be called regularly in order
	 * for voltage compensation to work properly - unlike the ordinary set
	 * function, it is not "set it and forget it."
	 */
    public void setVoltage(double voltage) {
		LogKitten.v("Motor " + getName() + " @ " + voltage + "v");
        for (var motor : motors) {
            motor.setVoltage(voltage);
        }
    }
    

    /// COMMANDS
    // TODO: remove the "Replaces *.java in pre-2023 standard" and "eg. button1.onTrue(motor.c_idle())" when they become unnecessary.

    /**
     * Disable motor using the underlying .disable(); command version of .disable().
     * 
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_disable())
     */
    public Command c_disable() { return this.run(() -> this.disable()); }

    /**
     * Stop motor using the underlying .stopMotor(); command version of .stopMotor().
     * 
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_stopMotor())
     */
    public Command c_stopMotor() { return this.run(() -> this.stopMotor()); }

    /**
     * Set motor power to zero. Replaces MotorIdle.java in pre-2023 standard.
     * 
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_idle())
     */
    public Command c_idle() { return this.runOnce(() -> setPower(0)); }
    
    /**
     * Set motor power to power. Command version of setPower().
     * 
     * @param power The power to set, in the range [-1, 1].
     * 
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_setPower())
     */
    public Command c_setPower(double power) { return this.runOnce(() -> this.setPower(power)); }

    /**
     * Repeatedly motor power to power until inturrupted. Replaces MotorConstant.java in pre-2023 standard.
     * 
     * @param power The power to set, in the range [-1, 1].
     * 
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_setPowerHold())
     */
    public Command c_setPowerHold(double power) { return this.run(() -> this.setPower(power)); }
    
    /**
     * Repeatedly set motor voltage. Command version of setVoltagePower().
     * Estimates and remembers the power by dividing by battery voltage in case
     * .get() is later called.
     *
     * c_setVoltage (a runOnce version) is not provided as wpilib
     * MotorController recommends always repeatedly setting voltage.
     *
     * @param voltage The voltage to set.
     *
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_setVoltageHold())
     */
    public Command c_setVoltageHold(double voltage) { return this.run(() -> this.setVoltage(voltage)); }

    // /// ERRORS (copied from 2022 standard Motor.java, not clear what the use is)
	// protected class UnsynchronizedMotorControllerRuntimeException extends RuntimeException {
	// 	private static final long serialVersionUID = 8688590919561059584L;

	// 	public UnsynchronizedMotorControllerRuntimeException() {
	// 		super(getName() + "'s MotorControllers report different speeds");
	// 	}
	// }

	// @Deprecated
	// protected class StrangeCANMotorControllerModeRuntimeException extends RuntimeException {
	// 	private static final long serialVersionUID = -539917227288371271L;

	// 	public StrangeCANMotorControllerModeRuntimeException() {
	// 		super("One of " + getName()
	// 				+ "'s MotorControllers is a CANMotorController with a non-zero mode. This might mess up it's .get(), so Motor cannot verify safety.");
	// 	}
	// }
}
