package org.usfirst.frc4904.standard.commands.solenoid;


import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;

/**
 * Command to set the state of a SolenoidSubsystem to RETRACT(DoubleSolenoid.Value.kReverse)
 */
public class SolenoidRetract extends SolenoidSet {
	/**
	 * Command to set the state of a SolenoidSubsystem to RETRACT(DoubleSolenoid.Value.kReverse)
	 * 
	 * @param name
	 *               Name of Command
	 * @param system
	 *               SolenoidSubsystem to set
	 */
	public SolenoidRetract(String name, SolenoidSubsystem system) {
		super(name, system, SolenoidState.RETRACT);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to RETRACT(DoubleSolenoid.Value.kReverse)
	 * 
	 * @param system
	 *               SolenoidSubsystem to set
	 */
	public SolenoidRetract(SolenoidSubsystem system) {
		this("SolenoidRetract", system);
	}
}