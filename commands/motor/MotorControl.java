package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.HashSet;
import java.util.Set;

/**
 * Controls a Motor directly from a Controller (e.g. Joystick or Xbox)
 *
 *
 */
public class MotorControl implements Command {
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
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		this.scale = scale;
		LogKitten.d("MotorControl created.");
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
	public void initialize() {
		LogKitten.d("MotorControl initialized");
		if (motor instanceof PositionSensorMotor) {
			((PositionSensorMotor) motor).disableMotionController();
		}
	}

	@Override
	public void execute() {
		LogKitten.d("MotorControl executing: " + controller.getAxis(axis));
		motor.set(controller.getAxis(axis) * scale);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		if(interrupted) {
			LogKitten.d("MotorControl interrupted");
		}
	}

	@Override
	public Set<Subsystem> getRequirements() {
		Set<Subsystem> motors = new HashSet<Subsystem>();
		motors.add(motor);
		return motors;
	}
}