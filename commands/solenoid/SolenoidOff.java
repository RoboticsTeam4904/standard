package org.usfirst.frc4904.standard.commands.solenoid;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;


public class SolenoidOff extends SolenoidSet {
	public SolenoidOff(String name, SolenoidSubsystem system) {
		super(name, system, SolenoidState.OFF);
	}

	public SolenoidOff(SolenoidSubsystem system) {
		this("SolenoidExtend", system);
	}
}