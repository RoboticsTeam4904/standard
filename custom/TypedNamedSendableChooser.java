package org.usfirst.frc4904.standard.custom;


import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * A sendable chooser for any named object.
 *
 * @param <T>
 */
public class TypedNamedSendableChooser<T extends Nameable> extends SendableChooser<T> {
	/**
	 * Adds an object of the type
	 * to the smart dashboard.
	 *
	 * @param object
	 */
	public void addObject(T object) {
		super.addObject(object.getName(), object);
	}

	/**
	 * Adds an object of the type
	 * to the smart dashboard as
	 * the default object.
	 *
	 * @param object
	 */
	public void addDefault(T object) {
		super.addDefault(object.getName() + " (default)", object);
	}
}
