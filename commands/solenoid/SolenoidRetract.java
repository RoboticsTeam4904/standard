package org.usfirst.frc4904.standard.commands.solenoid;

import java.util.function.BooleanSupplier;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;

/**
 * Command to set the state of a SolenoidSubsystem to
 * RETRACT(DoubleSolenoid.Value.kReverse)
 */
public class SolenoidRetract extends SolenoidSet {
	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * RETRACT(DoubleSolenoid.Value.kReverse)
	 * 
	 * @param name             Name of the retraction
	 * @param system           SolenoidSubsystem to set
	 * @param booleanSuppliers conditions that if true, prevent the setting of the
	 *                         system
	 */
	public SolenoidRetract(String name, SolenoidSubsystem system, BooleanSupplier... booleanSuppliers) {
		super(name, system, SolenoidState.RETRACT, booleanSuppliers);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * RETRACT(DoubleSolenoid.Value.kReverse)
	 * 
	 * @param system           SolenoidSubsystem to set
	 * @param booleanSuppliers conditions that if true, prevent the setting of the
	 *                         system
	 */
	public SolenoidRetract(SolenoidSubsystem system, BooleanSupplier... booleanSuppliers) {
		super("Retracting " + system.getName(), system, SolenoidState.RETRACT, booleanSuppliers);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * RETRACT(DoubleSolenoid.Value.kReverse)
	 * 
	 * @param system SolenoidSubsystem to set
	 */
	public SolenoidRetract(SolenoidSubsystem system) {
		super("Retracting " + system.getName(), system, SolenoidState.RETRACT);
	}
}