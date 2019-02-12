package org.usfirst.frc4904.standard.commands.solenoid;


import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;

/**
 * Command to set the state of a SolenoidSubsystem to OFF(DoubleSolenoid.Value.kOff)
 */
public class SolenoidOff extends SolenoidSet {
	/**
	 * Command to set the state of a SolenoidSubsystem to OFF(DoubleSolenoid.Value.kOff)
	 * 
	 * @param name
	 *               Name of Command
	 * @param system
	 *               SolenoidSubsystem to set
	 */
	public SolenoidOff(String name, SolenoidSubsystem system) {
		super(name, system, SolenoidState.OFF);
	}

	/**
	 * Command to set the state of a SolenoidSubsystem to OFF(DoubleSolenoid.Value.kOff)
	 * 
	 * @param name
	 *               Name of Command
	 * @param system
	 *               SolenoidSubsystem to set
	 */
	public SolenoidOff(SolenoidSubsystem system) {
		this("SolenoidOff", system);
	}
}