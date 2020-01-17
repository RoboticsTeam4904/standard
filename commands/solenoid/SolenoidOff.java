package org.usfirst.frc4904.standard.commands.solenoid;

import java.util.function.BooleanSupplier;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;

/**
 * Command to set the state of a SolenoidSubsystem to
 * OFF(DoubleSolenoid.Value.kOff)
 */
public class SolenoidOff extends SolenoidSet {
	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * OFF(DoubleSolenoid.Value.kOff)
	 * 
	 * @param name             Name of system to be turned off
	 * @param system           SolenoidSubsystem to set
	 * @param booleanSuppliers conditions that if true, prevent the setting of the
	 *                         system
	 */
	public SolenoidOff(String name, SolenoidSubsystem system, BooleanSupplier... booleanSuppliers) {
		super(name, system, SolenoidState.OFF, booleanSuppliers);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * OFF(DoubleSolenoid.Value.kOff)
	 * 
	 * @param system           SolenoidSubsystem to set
	 * @param booleanSuppliers conditions that if true, prevent the setting of the
	 *                         system
	 */
	public SolenoidOff(SolenoidSubsystem system, BooleanSupplier... booleanSuppliers) {
		this("Turning off " + system.getName(), system, booleanSuppliers);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * OFF(DoubleSolenoid.Value.kOff)
	 * 
	 * @param system SolenoidSubsystem to set
	 */
	public SolenoidOff(SolenoidSubsystem system) {
		this("Turning off " + system.getName(), system);
	}
}