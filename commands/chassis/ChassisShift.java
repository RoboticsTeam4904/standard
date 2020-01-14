package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command shifts a set of solenoids.
 *
 */
public class ChassisShift extends CommandBase {
	protected final SolenoidShifters solenoids;
	protected final SolenoidShifters.SolenoidState state;

	/**
	 * Shifts the solenoids to the provided state
	 * 
	 * @param name
	 * @param solenoids
	 * @param state
	 */
	public ChassisShift(String name, SolenoidShifters solenoids, SolenoidShifters.SolenoidState state) {
		super();
		this.state = state;
		this.solenoids = solenoids;
		setName(name);
		addRequirements(solenoids);
	}

	/**
	 * Shifts the solenoids to the provided state
	 *
	 * @param solenoids
	 * @param state
	 */
	public ChassisShift(SolenoidShifters solenoids, SolenoidShifters.SolenoidState state) {
		this("ChassisShift", solenoids, state);
	}

	/**
	 * Toggles the solenoids
	 * 
	 * @param name
	 * @param solenoids
	 */
	public ChassisShift(String name, SolenoidShifters solenoids) {
		this(name, solenoids, null);
	}

	/**
	 * Toggles the solenoids
	 * 
	 * @param solenoids
	 */
	public ChassisShift(SolenoidShifters solenoids) {
		this("ChassisShift", solenoids);
	}

	@Override
	public void initialize() {
		if (state == null) {
			// null state means toggle
			solenoids.set(); // TODO: needs to be implemented
		} else {
			// not null state means shift to it directly
			solenoids.set(state);
		}
	}

	@Override
	public boolean isFinished() {
		return false; // Encoders stay in whatever state until shifted elsewhere.
	}
}
