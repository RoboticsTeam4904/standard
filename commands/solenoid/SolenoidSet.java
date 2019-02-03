package org.usfirst.frc4904.standard.commands.solenoid;


import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;
import edu.wpi.first.wpilibj.command.Command;

public class SolenoidSet extends Command {
	protected final SolenoidSubsystem system;
	protected final SolenoidState state;

	public SolenoidSet(String name, SolenoidSubsystem system, SolenoidState state) {
		super(name, system);
		this.system = system;
		this.state = state;
	}

	public SolenoidSet(SolenoidSubsystem system, SolenoidState state) {
		this("SolenoidSet", system, state);
	}

	@Override
	public void initialize() {
		system.set(state);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
