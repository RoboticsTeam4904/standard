package org.usfirst.frc4904.standard.commands.solenoid;

import java.util.function.BooleanSupplier;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Command to set the state of a SolenoidSubsystem
 * TO DO: rewrite using inline commands on SolenoidSubsystem
 */

public class SolenoidSet extends Command {
	protected final SolenoidSubsystem system;
	protected final SolenoidState state;
	protected final BooleanSupplier[] booleanSuppliers;

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param name             Name of the SolenoidSubsystem
	 * @param system           SolenoidSubsystem to set
	 * @param state            state to set system
	 * @param booleanSuppliers conditions that if true, prevents solenoidSubsystem
	 *                         from setting
	 */
	public SolenoidSet(String name, SolenoidSubsystem system, SolenoidState state,
			BooleanSupplier... booleanSuppliers) {
		super();
		setName(name);
		this.system = system;
		this.state = state;
		this.booleanSuppliers = booleanSuppliers;

		addRequirements(system);
	}

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param system           SolenoidSubsystem to set
	 * @param state            state to set system
	 * @param booleanSuppliers conditions that if true, prevents solenoidSubsystem
	 *                         from setting
	 */
	public SolenoidSet(SolenoidSubsystem system, SolenoidState state, BooleanSupplier... booleanSuppliers) {
		this("SolenoidSet", system, state, booleanSuppliers);
	}

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param name   Name of the SolenoidSubsystem
	 * @param system SolenoidSubsystem to set
	 * @param state  state to set system
	 */
	public SolenoidSet(String name, SolenoidSubsystem system, SolenoidState state) {
		this(name, system, state, () -> {
			return false;
		});
	}

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param system SolenoidSubsystem to set
	 * @param state  state to set system
	 */
	public SolenoidSet(SolenoidSubsystem system, SolenoidState state) {
		this("SolenoidSet", system, state);
	}

	/**
	 * Sets the state of the system
	 */
	@Override
	public void initialize() {
		for (BooleanSupplier booleanSupplier : booleanSuppliers) {
			if (booleanSupplier.getAsBoolean()) {
				return;
			}
		}
		system.set(state);
	}

	/**
	 * Returns false to prevent default command from running
	 */
	@Override
	public boolean isFinished() {
		return false;
	}
}
