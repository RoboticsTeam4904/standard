package org.usfirst.frc4904.standard.commands.motor;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorConstant extends Command {
	private final double motorSpeed;
	private final SpeedController motor;
	
	public <A extends Subsystem & SpeedController> MotorConstant(A motor, double motorSpeed) {
		this.motor = motor;
		this.motorSpeed = motorSpeed;
		requires(motor);
		setInterruptible(true);
	}
	
	protected void initialize() {
		motor.set(motorSpeed);
	}
	
	protected void execute() {
		motor.set(motorSpeed);
	}
	
	protected void interrupted() {}
	
	protected void end() {}
	
	protected boolean isFinished() {
		return false;
	}
}
