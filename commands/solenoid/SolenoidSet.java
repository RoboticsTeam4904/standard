package org.usfirst.frc4904.standard.commands.solenoid;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class SolenoidSet extends Command {
	protected final SolenoidSubsystem system;
	protected final SolenoidState state;

	public SolenoidSet(SolenoidSubsystem system, SolenoidState state) {
		this.system = system;
		this.state = state;
		requires(system);
	}

	public void set(SolenoidState state) {
		system.setState(state);
		LogKitten.d(state); // TODO: make a better message
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
