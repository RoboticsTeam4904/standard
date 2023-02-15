package org.usfirst.frc4904.standard.subsystems.motor;

import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class BrakeableMotorSubsystem extends MotorSubsystem {
    // TODO: should this take general MotorControllers and just brake the ones that implement BrakeableMotorController
    protected final BrakeableMotorController[] motors;

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
	public BrakeableMotorSubsystem(String name, SpeedModifier speedModifier, BrakeableMotorController... motors) {
		super(name, speedModifier, motors);
        this.motors = motors;
        setDefaultCommand(Commands.runOnce(() -> this.neutralOutput()));
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
	public BrakeableMotorSubsystem(String name, BrakeableMotorController... motors) {
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
    public BrakeableMotorSubsystem(SpeedModifier speedModifier, BrakeableMotorController... motors) {
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
	public BrakeableMotorSubsystem(BrakeableMotorController... motors) {
		this("Motor", motors);
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
    
    /**
     * Enables brake mode on and brakes each motor. Command version of brake().
     *
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_brake())
     */
    public Command c_brake() {
        return this.runOnce(() -> this.brake());
    }
    /**
     * Enables coast mode on and coasts each motor. Command version of coast().
     *
     * @return Command to be scheduled or triggered, eg. button1.onTrue(motor.c_coast())
     */
    public Command c_coast() {
        return this.runOnce(() -> this.coast());
    }
}
