package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Controls a Motor directly from a Controller (e.g. Joystick or Xbox)
 *
 *
 */
public class MotorControl extends Command {
	protected final Motor motor;
	protected final Controller controller;
	protected final int axis;
	protected final boolean invert;
	
	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param invert
	 */
	public MotorControl(Motor motor, Controller controller, int axis, boolean invert) {
		super("MotorControl");
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		this.invert = invert;
		requires(motor);
		setInterruptible(true);
		LogKitten.v("MotorControl created for " + motor.getName());
	}
	
	@Override
	protected void initialize() {
		LogKitten.v("MotorControl initialized");
	}
	
	@Override
	protected void execute() {
		LogKitten.d("MotorControl executing: " + controller.getAxis(axis));
		if (!invert) {
			motor.set(controller.getAxis(axis));
		} else {
			motor.set(-1.0f * controller.getAxis(axis));
		}
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {
		LogKitten.w("MotorControl interrupted");
	}
}