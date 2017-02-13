package org.usfirst.frc4904.standard.humaninput;


import org.usfirst.frc4904.standard.custom.Nameable;

/**
 * A generic human interface class.
 * This is designed to be used to
 * bind commands to controllers.
 * bindCommands should only be called
 * during teleop init.
 *
 */
public abstract class HumanInput implements Nameable {
	protected final String name;

	public HumanInput(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * A function where the driver's and operator's controls are bound to commands
	 * Can't be done in the constructor because constructors are called too early
	 */
	public abstract void bindCommands();
}