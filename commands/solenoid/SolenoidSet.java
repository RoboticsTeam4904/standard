package org.usfirst.frc4904.standard.commands.solenoid;

import java.util.function.BooleanSupplier;
import java.util.Set;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Command to set the state of a SolenoidSubsystem
 */
public class SolenoidSet extends CommandBase {
	protected final SolenoidSubsystem system;
	protected final SolenoidState state;
	protected final BooleanSupplier[] booleanSuppliers;
	protected final Set<Subsystem> requirements;

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param name             Name of the Command
	 * @param system           SolenoidSubsystem to set
	 * @param state            state to set system
	 * @param booleanSuppliers conditions that if true, prevents solenoidSubsystem
	 *                         from setting
	 */
	public SolenoidSet(String name, SolenoidSubsystem system, SolenoidState state, BooleanSupplier... booleanSuppliers) {
		this.system = system;
		this.state = state;
		this.booleanSuppliers = booleanSuppliers;

		requirements = Set.of((Subsystem) system);
	}

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param name             Name of the Command
	 * @param system           SolenoidSubsystem to set
	 * @param state            state to set system
	 * @param booleanSuppliers conditions that if true, prevents solenoidSubsystem
	 *                         from setting
	 */
	public SolenoidSet(SolenoidSubsystem system, SolenoidState state, BooleanSupplier... booleanSuppliers) {
		this("SolenoidSet", system, state, booleanSuppliers);
	}

	public SolenoidSet(SolenoidSubsystem system, SolenoidState state) {
		this(system, state, () -> {
			return false;
		});
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
	 * Gets requirements
	 */
	public Set<Subsystem> getRequirements() {
		return requirements;
	}

	/**
	 * Returns false to prevent default command from running
	 */
	@Override
	public boolean isFinished() {
		return false;
	}
}
