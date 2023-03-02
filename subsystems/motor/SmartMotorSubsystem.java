package org.usfirst.frc4904.standard.subsystems.motor;

import java.util.function.DoubleSupplier;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.motorcontrollers.SmartMotorController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public abstract class SmartMotorSubsystem<SMC extends SmartMotorController> extends SubsystemBase {    // generic to allow inherited class (eg. TalonMotorSubsystem) to directly use TalonMotorController APIs on super.motors (not possible if this.motors here was BrakeableMotorController)
    protected final SMC[] motors;
	protected final SpeedModifier speedModifier;    // NOTE: maybe change to be called PowerModifier
	protected final String name;
    // TODO: should this take general MotorControllers and just brake the ones that implement BrakeableMotorController

    /**
     * A class that wraps around a variable number of BrakeableMotorController
     * objects to give them Subsystem functionality. Can also modify their power
     * with a SpeedModifier for things like scaling or brownout protection.
     * Enables brake mode on each motor by default. 
     *
     * @param name          The name for the motor
     * @param speedModifier A SpeedModifier changes the input to every motor based
     *                      on some factor. The default is an IdentityModifier,
     *                      which does not affect anything.
     * @param motors        The MotorControllers in this subsystem. Can be a single
     *                      MotorController or multiple MotorControllers.
     */
	public SmartMotorSubsystem(String name, SpeedModifier speedModifier, SMC... motors) {
		super();
		setName(name);
		this.name = name;
		this.speedModifier = speedModifier;
		this.motors = motors;
		for (var motor : motors) {
			motor.set(0);
		}
		setDefaultCommand(this.c_idle());

        setDefaultCommand(this.runOnce(() -> this.neutralOutput()));
    }

	/**
	 * A class that wraps around a variable number of BrakeableMotorController
	 * objects to give them Subsystem functionality. Can also modify their speed
	 * with a SpeedModifier for things like scaling or brownout protection.
	 * Enables brake mode on each motor by default. 
	 *
	 * @param name       The name for the motor
	 * @param motors     The MotorControllers in this subsystem. Can be a single
	 *                   MotorController or multiple MotorControllers.
	 */
	public SmartMotorSubsystem(String name, SMC... motors) {
		this(name, new IdentityModifier(), motors);
	}

	/**
	 * A class that wraps around a variable number of BrakeableMotorController
	 * objects to give them Subsystem functionality. Can also modify their speed
	 * with a SpeedModifier for things like scaling or brownout protection.
	 * Enables brake mode on each motor by default. 
	 *
	 * @param speedModifier A SpeedModifier changes the input to every motor based
	 *                      on some factor. The default is an IdentityModifier,
	 *                      which does not affect anything.
	 * @param motors        The MotorControllers in this subsystem. Can be a single
	 *                      MotorController or multiple MotorControllers.
	 */
    public SmartMotorSubsystem(SpeedModifier speedModifier, SMC... motors) {
        this("Motor", speedModifier, motors);
    }

	/**
	 * A class that wraps around a variable number of BrakeableMotorController
	 * objects to give them Subsystem functionality. Can also modify their speed
	 * with a SpeedModifier for things like scaling or brownout protection.
	 * Enables brake mode on each motor by default. 
	 *
	 * @param motors The MotorControllers in this subsystem. Can be a single
	 *               MotorController or multiple MotorControllers.
	 */
	public SmartMotorSubsystem(SMC... motors) {
		this("Motor Subsystem", motors);
	} 
    
    /// METHODS
	/**
	 * Disables the motor with underlying .disable()
	 */
	public void disable() { for (var motor : motors) motor.disable(); }

	/**
	 * Stops the motor with underlying stopMotor()
     *
	 * In theory this should stop the motor without disabling, but wpilib seems
	 * to just call disable under the hood.
	 */
	public void stopMotor() { for (var motor : motors) motor.stopMotor(); }

	// if you implement a .get() to get the power, make sure you update it in setVoltage() too (eg. with voltage/RobotController.getBatteryVoltage())

	/**
	 * Set the motor power. Passes through SpeedModifier.
	 *
	 * @param power The power to set. Value should be between -1.0 and 1.0.
	 */
	public void set(double power) {
		LogKitten.v("Motor " + getName() + " @ " + power);
		double newPower = speedModifier.modify(power);
		for (var motor : motors) motor.set(newPower);
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
    /**
     * Sets the neutral output mode to brake. Motor will continue to run, but
     * future calls to neutralOutput() will cause motor to brake.
     */
    public void setBrakeOnNeutral() { for (var motor : motors) motor.setBrakeOnNeutral(); }
    /**
     * Sets the neutral output mode to coast. Motor will continue to run, but
     * future calls to neutralOutput() will cause motor to coast.
     */
    public void setCoastOnNeutral() { for (var motor : motors) motor.setCoastOnNeutral(); }

    /**
     * Sets the output mode to neutral, which is either break or coast (default
     * brake). Use setBrakeModeOnNeutral or setCoastModeOnNeutral to set this
     * mode.
     *
     * Uses the underlying .neutralOutput() method.
     */
    public void neutralOutput() { for (var motor : motors) motor.neutralOutput(); }
    
    /**
     * Enables brake mode on and brakes each motor.
     */
    public void brake() {
        setBrakeOnNeutral();
        neutralOutput();
    }
    
    /**
     * Enables coast mode on and coasts each motor.
     */
    public void coast() {
        setCoastOnNeutral();
        neutralOutput();
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
    public Command c_holdPower(double power) { return this.run(() -> this.setPower(power)); }
    
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
    public Command c_holdVoltage(double voltage) { return this.run(() -> this.setVoltage(voltage)); }

    /**
     * Enables brake mode on and brakes each motor. Command version of brake().
     *
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_brake())
     */
    public Command c_brake() { return this.runOnce(() -> this.brake()); }

    /**
     * Enables coast mode on and coasts each motor. Command version of coast().
     *
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_coast())
     */
    public Command c_coast() { return this.runOnce(() -> this.coast()); }

    /**
     * Write PIDF values to hardware, which will be used with the closed loop control commands
     * 
     * TODO: replace with ezControl
     */
    public abstract void setRPM(double voltage);
    public abstract void configPIDF(double p, double i, double d, double f);
    public abstract Command c_controlRPM(DoubleSupplier setpointSupplier);
    public abstract Command c_holdRPM(double setpoint);
    public abstract Command c_controlPosition(DoubleSupplier setpointSupplier);
    public abstract Command c_holdPosition(double setpoint);

    // deprecate the set-and-forget commands, because commands should not end before the motor is in a "stop" state
    @Deprecated public abstract Command c_setRPM(double setpoint);
    @Deprecated public abstract Command c_setPosition(double setpoint);
}
