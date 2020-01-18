package org.usfirst.frc4904.standard.custom;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import java.util.function.Supplier;

/**
 * A sendable chooser for any named object.
 *
 * @param <T>
 */
public class TypedNamedSendableChooser<T extends Nameable> extends SendableChooser<T> implements Supplier<T> {
	/**
	 * Adds an object of the type to the smart dashboard.
	 *
	 * @param object
	 */
	public void addOption(T object) {
		super.addOption(object.getName(), object);
	}

	/**
	 * Adds an object of the type to the smart dashboard as the default object.
	 *
	 * @param object
	 */
	public void setDefaultOption(T object) {
		super.setDefaultOption(object.getName() + " (default)", object);
	}

	/**
	 * Wrapper for getSelected() to conform to Supplier<T>
	 */
	public T get() {
		return getSelected();
	}
}
