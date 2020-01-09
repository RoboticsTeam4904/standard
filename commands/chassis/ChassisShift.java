package org.usfirst.frc4904.standard.commands.chassis;

import java.util.Set;
import java.util.HashSet;

import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This command shifts a set of solenoids.
 *
 */
public class ChassisShift implements Command {
	protected final SolenoidShifters solenoids;
	protected final SolenoidShifters.ShiftState state;

	/**
	 * Toggles the solenoids
	 * 
	 * @param solenoids
	 */
	public ChassisShift(SolenoidShifters solenoids) {
		this(solenoids, null);
	}

	/**
	 * Shifts the solenoids to the provided state
	 *
	 * @param solenoids
	 * @param state
	 */
	public ChassisShift(SolenoidShifters solenoids, SolenoidShifters.ShiftState state) {
		super();
		this.solenoids = solenoids;
		//addRequirements(solenoids);
		this.state = state;
	}

	@Override
	public void initialize() {
		if (state == null) {
			// null state means toggle
			solenoids.shift();
		} else {
			// not null state means shift to it directly
			solenoids.shift(state);
		}
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return false; // Encoders stay in whatever state until shifted elsewhere.
	}

	@Override
	public Set<Subsystem> getRequirements() {
		HashSet<Subsystem> set = new HashSet<Subsystem>();
		set.add(this.solenoids);
		return set;
	}
}
