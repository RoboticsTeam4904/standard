package org.usfirst.frc4904.standard.commands.solenoid;

import java.util.function.BooleanSupplier;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to set the state of a SolenoidSubsystem
 */
public class SolenoidSet extends Command {
	protected final SolenoidSubsystem system;
	protected final SolenoidState state;
	protected final BooleanSupplier[] booleanSuppliers;
	double startingTime;

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param name             Name of the Command
	 * @param system           SolenoidSubsystem to set
	 * @param state            state to set system
	 * @param booleanSuppliers conditions that if true, prevents solenoidSubsystem
	 *                         from setting
	 */
	public SolenoidSet(String name, SolenoidSubsystem system, SolenoidState state,
			BooleanSupplier... booleanSuppliers) {
		super(name, system);
		this.system = system;
		this.state = state;
		this.booleanSuppliers = booleanSuppliers;
	}

	/**
	 * Sets the state of a SolenoidSubsystem
	 * 
	 * @param system SolenoidSubsystem to set
	 * @param state  state to set system
	 */
	public SolenoidSet(SolenoidSubsystem system, SolenoidState state, BooleanSupplier... booleanSuppliers) {
		this("SolenoidSet", system, state, booleanSuppliers);
	}

	public SolenoidSet(String name, SolenoidSubsystem system, SolenoidState state, double delay) {
		this(name, system, state, () -> {
			return false;
		});
	}

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
		startingTime = System.currentTimeMillis();
	}

	/**
	 * Returns true after a preset delay time
	 */
	@Override
	protected boolean isFinished() {
		if(System.currentTimeMillis() > this.startingTime + system.delay) {
			return true;
		}
		return false;
	}
}
