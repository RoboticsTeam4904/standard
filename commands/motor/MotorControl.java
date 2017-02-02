package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
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
	protected final double scale;

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param scale
	 */
	public MotorControl(Motor motor, Controller controller, int axis, double scale) {
		super("MotorControl");
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		this.scale = scale;
		requires(motor);
		setInterruptible(true);
		LogKitten.d("MotorControl created for " + motor.getName());
	}

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param scale
	 */
	public MotorControl(Motor motor, Controller controller, int axis) {
		this(motor, controller, axis, 1.0);
	}

	@Override
	protected void initialize() {
		LogKitten.d("MotorControl initialized");
		if (motor instanceof PositionSensorMotor) {
			((PositionSensorMotor) motor).disableMotionController();
		}
	}

	@Override
	protected void execute() {
		LogKitten.d("MotorControl executing: " + controller.getAxis(axis));
		motor.set(controller.getAxis(axis) * scale);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {
		LogKitten.d("MotorControl interrupted");
	}
}