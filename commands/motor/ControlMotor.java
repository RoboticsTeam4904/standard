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
	
	public <A extends Subsystem & SpeedController> ControlMotor(A motor, Controller controller, int axis) {
		super("MotorInPipe");
		this.motor = motor;
		this.controller = controller;
		this.axis = axis;
		requires(motor);
		setInterruptible(true);
		logger = new LogKitten(LogKitten.LEVEL_DEBUG, LogKitten.LEVEL_DEBUG);
		logger.v("ControlMotor created for " + motor.getName());
	}
	
	protected void initialize() {
		logger.v("MotorInPipe initialized");
		System.out.println("ControlMotor initlialized");
	}
	
	protected void execute() {
		logger.d("ControlMotor executing: " + controller.getAxis(axis), true);
		motor.set(controller.getAxis(axis));
	}
	
	protected void end() {}
	
	protected void interrupted() {
		logger.w("ControlMotor interrupted");
	}
	
	protected boolean isFinished() {
		return false;
	}
}