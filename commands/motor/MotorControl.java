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
	protected final double offset;

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param name
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param scale
	 * @param offset
	 *        A constant to add to the motor's speed
	 *        Useful for using a controller for fine-tuning a constant speed
	 */
	public MotorControl(String name, Motor motor, Controller controller, int axis, double scale, double offset) {
		super(name);
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		this.scale = scale;
		this.offset = offset;
		requires(motor);
		setInterruptible(true);
		LogKitten.d("MotorControl created for " + motor.getName());
	}

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param name
	 * @param motor
	 * @param controller
	 * @param axis
	 */
	public MotorControl(String name, Motor motor, Controller controller, int axis) {
		this(name, motor, controller, axis, 1.0, 0.0);
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
	public MotorControl(Motor motor, Controller controller, int axis, double scale) {
		this("MotorControl", motor, controller, axis, 1.0, 0.0);
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
		motor.set(controller.getAxis(axis) * scale + offset);
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