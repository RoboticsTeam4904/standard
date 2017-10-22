package org.usfirst.frc4904.standard.custom.controllers;

import java.util.function.Supplier;

public class ControllerDoubleSupplier implements Supplier<Double> {
	protected final Controller controller;
	protected final int axis;
	protected final double scale;

	/**
	 * Wrapper for a controller that allows it to act as a double supplier for a
	 * specific axis set to a certain scale.
	 * 
	 * @param controller
	 * @param axis
	 * @param scale
	 */
	public ControllerDoubleSupplier(Controller controller, int axis, double scale) {
		this.controller = controller;
		this.axis = axis;
		this.scale = scale;
	}

	/**
	 * Wrapper for a controller that allows it to act as a double supplier for a
	 * specific axis. Scale assumed as 1.
	 * 
	 * @param controller
	 * @param axis
	 */
	public ControllerDoubleSupplier(Controller controller, int axis) {
		this(controller, axis, 1.0);
	}

	/**
	 * Returns a double for the value of the axis.
	 */
	@Override
	public Double get() {
		return controller.getAxis(axis) * scale;
	}
}
