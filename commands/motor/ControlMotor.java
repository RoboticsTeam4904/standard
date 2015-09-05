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
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_VERBOSE);
		logger.v("MotorInPipe created for " + motor.getName(), true);
	}
	
	protected void initialize() {
		logger.v("MotorInPipe initialized", true);
		System.out.println("MotorInPipe initlialized");
	}
	
	protected void execute() {
		System.out.println("Motor: " + controller.getAxis(axis));
		motor.set(controller.getAxis(axis));
	}
	
	protected void end() {}
	
	protected void interrupted() {
		logger.w("MotorInPipe interrupted", true);
	}
	
	protected boolean isFinished() {
		return false;
	}
}