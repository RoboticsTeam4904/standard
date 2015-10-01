package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ControlMotor extends Command {
	private final SpeedController motor;
	private final LogKitten logger;
	private final Controller controller;
	private final int axis;
	private final boolean invert;
	
	public <A extends Subsystem & SpeedController> ControlMotor(A motor, Controller controller, int axis, boolean invert) {
		super("MotorInPipe");
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		this.invert = invert;
		requires(motor);
		setInterruptible(true);
		logger = new LogKitten(LogKitten.LEVEL_DEBUG, LogKitten.LEVEL_DEBUG);
		logger.v("ControlMotor created for " + motor.getName());
	}
	
	protected void initialize() {
		logger.v("ControlMotor initialized");
		System.out.println("ControlMotor initlialized");
	}
	
	protected void execute() {
		logger.d("ControlMotor executing: " + controller.getAxis(axis));
		if (!invert) {
			motor.set(controller.getAxis(axis));
		} else {
			motor.set(-1.0f * controller.getAxis(axis));
		}
	}
	
	protected void end() {}
	
	protected void interrupted() {
		logger.w("ControlMotor interrupted");
	}
	
	protected boolean isFinished() {
		return false;
	}
}