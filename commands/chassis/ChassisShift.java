package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command shifts a set of solenoids.
 *
 */
public class ChassisShift extends Command {
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
		super("ChassisShift");
		this.solenoids = solenoids;
		requires(solenoids);
		setInterruptible(true);
		this.state = state;
	}

	@Override
	protected void initialize() {
		if (state == null) {
			// null state means toggle
			solenoids.shift();
		} else {
			// not null state means shift to it directly
			solenoids.shift(state);
		}
	}

	@Override
	protected void execute() {}

	@Override
	protected void interrupted() {}

	@Override
	protected void end() {}

	@Override
	protected boolean isFinished() {
		return false; // Encoders stay in whatever state until shifted elsewhere.
	}
}
