package org.usfirst.frc4904.standard.custom.controllers;


import java.util.function.Supplier;

public class ControllerDoubleSupplier implements Supplier<Double> {
	protected final Controller controller;
	protected final int axis;

	public ControllerDoubleSupplier(Controller controller, int axis) {
		this.controller = controller;
		this.axis = axis;
	}

	@Override
	public Double get() {
		return controller.getAxis(axis);
	}
}
