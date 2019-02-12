package org.usfirst.frc4904.standard.commands.solenoid;


import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to set the state of a SolenoidSubsystem
 */
public class SolenoidSet extends Command {
	protected final SolenoidSubsystem system;
	protected final SolenoidState state;

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param name
	 *               Name of the Command
	 * @param system
	 *               SolenoidSubsystem to set
	 * @param state
	 *               state to set system
	 */
	public SolenoidSet(String name, SolenoidSubsystem system, SolenoidState state) {
		super(name, system);
		this.system = system;
		this.state = state;
	}

		/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param system
	 *               SolenoidSubsystem to set
	 * @param state
	 *               state to set system
	 */
	public SolenoidSet(SolenoidSubsystem system, SolenoidState state) {
		this("SolenoidSet", system, state);
	}

	/**
	 * Sets the state of the system
	 */
	@Override
	public void initialize() {
		system.set(state);
	}

	/**
	 * Returns false to prevent default command from running
	 */
	@Override
	protected boolean isFinished() {
		return false;
	}
}
