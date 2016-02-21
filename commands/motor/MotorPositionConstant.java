package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import edu.wpi.first.wpilibj.command.Command;

public class MotorPositionConstant extends Command {
	protected SensorMotor motor;
	protected double position;
	
	public MotorPositionConstant(SensorMotor motor, double position) {
		super("MotorPositionConstant");
		this.motor = motor;
		this.position = position;
		motor.enablePID();
		setInterruptible(true);
	}
	
	@Override
	protected void initialize() {
		motor.setPosition(position);
	}
	
	@Override
	protected void execute() {}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
}
