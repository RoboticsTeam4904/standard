package org.usfirst.frc4904.standard.commands.solenoid;


import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;

public class SolenoidRetract extends SolenoidSet {
	public SolenoidRetract(String name, SolenoidSubsystem system) {
		super(name, system, SolenoidState.RETRACT);
	}

	public SolenoidRetract(SolenoidSubsystem system) {
		this("SolenoidRetract", system);
	}
}