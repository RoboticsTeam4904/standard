package org.usfirst.frc4904.standard.custom.controllers;


import java.util.function.Supplier;

public class ControllerDoubleSupplier implements Supplier<Double> {
	protected final Controller controller;
	protected final int axis;

	/**
	 * Wrapper for a controller that allows it to act as a double supplier
	 * for a specific axis.
	 * 
	 * @param controller
	 * @param axis
	 */
	public ControllerDoubleSupplier(Controller controller, int axis) {
		this.controller = controller;
		this.axis = axis;
	}

	/**
	 * Returns a double for the value of the axis.
	 */
	@Override
	public Double get() {
		return controller.getAxis(axis);
	}
}
