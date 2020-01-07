package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Controls a Motor directly from a Controller (e.g. Joystick or Xbox)
 * Has an acceleration cap (but not deceleration cap)
 *
 */
public class MotorControlAccelCap extends Command {
	protected final Motor motor;
	protected final Controller controller;
	protected final int axis;
	protected final double scale;
	protected final double accel_cap;
	protected double last_speed;
	protected long last_t;

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 * Has an acceleration cap (but not deceleration cap)
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param scale
	 * @param accel_cap
	 *        This is the maximum change in motor speed per second
	 */
	public MotorControlAccelCap(Motor motor, Controller controller, int axis, double scale, double accel_cap) {
		super("MotorControl");
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		this.scale = scale;
		this.accel_cap = accel_cap;
		this.last_speed = 0.0;
		last_t = System.currentTimeMillis();
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
	 * @param accel_cap
	 *        This is the maximum change in motor speed per second
	 */
	public MotorControlAccelCap(Motor motor, Controller controller, int axis, double accel_cap) {
		this(motor, controller, axis, 1.0, accel_cap);
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
		double human_input = controller.getAxis(axis) * scale;
		double new_speed;
		double delta_t = (System.currentTimeMillis() - last_t) / 1000.0;
		last_t = System.currentTimeMillis();
		new_speed = Math.min(last_speed + accel_cap * delta_t, human_input);
		motor.set(new_speed);
		last_speed = new_speed;
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