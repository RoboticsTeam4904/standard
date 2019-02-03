package org.usfirst.frc4904.standard.commands.solenoid;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;


public class SolenoidExtend extends SolenoidSet {
	public SolenoidExtend(String name, SolenoidSubsystem system) {
		super(name, system, SolenoidState.FORWARD);
	}

	public SolenoidExtend(SolenoidSubsystem system) {
		this("SolenoidExtend", system);
	}
}