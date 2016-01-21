package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls a motor (or AccelMotor or MotorGroup)
 * from a single controller (e.g. Joystick or Xbox)
 * 
 * 
 */
public class ControlMotor extends Command {
	private final SpeedController motor;
	private final Controller controller;
	private final int axis;
	private final boolean invert;
	
	/**
	 * Constructor.
	 * This command controls a motor based on
	 * the axis axis of the controller
	 * controller. This can allow an
	 * operator to easily control a single
	 * motor from an axis of the controller.
	 * 
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param invert
	 */
	public <A extends Subsystem & SpeedController> ControlMotor(A motor, Controller controller, int axis, boolean invert) {
		super("MotorInPipe");
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		this.invert = invert;
		requires(motor);
		setInterruptible(true);
		LogKitten.v("ControlMotor created for " + motor.getName());
	}
	
	protected void initialize() {
		LogKitten.v("ControlMotor initialized");
		System.out.println("ControlMotor initlialized");
	}
	
	protected void execute() {
		LogKitten.d("ControlMotor executing: " + controller.getAxis(axis));
		if (!invert) {
			motor.set(controller.getAxis(axis));
		} else {
			motor.set(-1.0f * controller.getAxis(axis));
		}
	}
	
	protected void end() {}
	
	protected void interrupted() {
		LogKitten.w("ControlMotor interrupted");
	}
	
	protected boolean isFinished() {
		return false;
	}
}