package org.usfirst.frc4904.standard.custom.controllers;


import java.util.function.Supplier;

public class ControllerDoubleSupplier implements Supplier<Double> {
	protected final Controller controller;
	protected final int axis;
	protected final double scale;
	protected final double offset;

	/**
	 * Wrapper for a controller that allows it to act as a double supplier for a
	 * specific axis set to a certain scale.
	 * 
	 * @param controller
	 * @param axis
	 * @param scale
	 */
	public ControllerDoubleSupplier(Controller controller, int axis, double scale, double offset) {
		this.controller = controller;
		this.axis = axis;
		this.scale = scale;
		this.offset = offset;
	}

	/**
	 * Wrapper for a controller that allows it to act as a double supplier for a
	 * specific axis. Offset assumed as 0.
	 * 
	 * @param controller
	 * @param axis
	 * @param scale
	 */
	public ControllerDoubleSupplier(Controller controller, int axis, double scale) {
		this(controller, axis, scale, 0.0);
	}

	/**
	 * Wrapper for a controller that allows it to act as a double supplier for a
	 * specific axis. Scale assumed as 1, offset as 0.
	 * 
	 * @param controller
	 * @param axis
	 */
	public ControllerDoubleSupplier(Controller controller, int axis) {
		this(controller, axis, 1.0, 0.0);
	}

	/**
	 * Returns a double for the value of the axis.
	 */
	@Override
	public Double get() {
		return controller.getAxis(axis) * scale + offset;
	}
}
