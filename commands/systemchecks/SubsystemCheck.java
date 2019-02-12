package org.usfirst.frc4904.standard.commands.systemchecks;


import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * SystemCheck that takes in Subsystems
 */
public abstract class SubsystemCheck extends SystemCheck {
	/**
	 * @param name
	 *                   name of check
	 * @param timeout
	 *                   duration of check
	 * @param subsystems
	 *                   subsystems being checked
	 */
	public SubsystemCheck(String name, double timeout, Subsystem... subsystems) {
		super(name, timeout, subsystems);
		for (Subsystem system : subsystems) {
			requires(system);
		}
	}

	/**
	 * @param name
	 *                   name of check
	 * @param subsystems
	 *                   subsystems being checked
	 */
	public SubsystemCheck(String name, Subsystem... systems) {
		this(name, DEFAULT_TIMEOUT, systems);
	}

	/**
	 * @param timeout
	 *                   duration of check
	 * @param subsystems
	 *                   subsystems being checked
	 */
	public SubsystemCheck(double timeout, Subsystem... systems) {
		this("SubsystemCheck", timeout, systems);
	}

	/**
	 * @param subsystems
	 *                   subsystems being checked
	 */
	public SubsystemCheck(Subsystem... systems) {
		this("SubsystemCheck", systems);
	}
}