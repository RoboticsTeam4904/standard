package org.usfirst.frc4904.standard.commands.solenoid;

import java.util.function.BooleanSupplier;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;

/**
 * Command to set the state of a SolenoidSubsystem to
 * EXTEND(DoubleSolenoid.Value.kFORWARD)
 */
public class SolenoidExtend extends SolenoidSet {
	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * EXTEND(DoubleSolenoid.Value.kForward)
	 * 
	 * @param name   Name of Command
	 * @param system SolenoidSubsystem to set * @param booleanSuppliers conditions
	 *               that if true, prevent the setting of the system
	 */
	public SolenoidExtend(String name, SolenoidSubsystem system, BooleanSupplier... booleanSuppliers) {
		super(name, system, SolenoidState.EXTEND, booleanSuppliers);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * EXTEND(DoubleSolenoid.Value.kForward)
	 * 
	 * @param name Name of Command
	 */
	public SolenoidExtend(String name, SolenoidSubsystem system) {
		super(name, system, SolenoidState.EXTEND);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * EXTEND(DoubleSolenoid.Value.kForward)
	 * 
	 * @param name   Name of Command
	 * @param system SolenoidSubsystem to set * @param booleanSuppliers conditions
	 *               that if true, prevent the setting of the system
	 */
	public SolenoidExtend(SolenoidSubsystem system, BooleanSupplier... booleanSuppliers) {
		super("SolenoidExtend", system, SolenoidState.EXTEND, booleanSuppliers);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to
	 * EXTEND(DoubleSolenoid.Value.kForward)
	 * 
	 * @param system SolenoidSubsystem to set
	 */
	public SolenoidExtend(SolenoidSubsystem system) {
		this("SolenoidExtend", system);
	}
}