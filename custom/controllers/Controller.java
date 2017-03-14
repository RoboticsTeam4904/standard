package org.usfirst.frc4904.standard.custom.controllers;


/**
 * A generic interface for a controller.
 *
 */
public interface Controller {
	public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	public static final int TWIST_AXIS = 2;

	/**
	 * Allows a generic controller to return an individual axis
	 *
	 * @param axis
	 *        (corresponding to standard axis
	 * @return a double (-1 to 1) representing the position of the axis
	 */
	public double getAxis(int axis);
}
