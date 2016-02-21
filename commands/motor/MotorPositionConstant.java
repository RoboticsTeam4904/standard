package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import edu.wpi.first.wpilibj.command.Command;

public class MotorPositionConstant extends Command {
	protected SensorMotor motor;
	protected double position;
	protected boolean end;
	
	public MotorPositionConstant(SensorMotor motor, double position, boolean end) {
		super("MotorPositionConstant");
		this.motor = motor;
		this.position = position;
		this.end = end;
		motor.enablePID();
		setInterruptible(true);
	}
	
	public MotorPositionConstant(SensorMotor motor, double position) {
		this(motor, position, true);
	}
	
	@Override
	protected void initialize() {
		motor.setPosition(position);
	}
	
	@Override
	protected void execute() {}
	
	@Override
	protected boolean isFinished() {
		if (end) {
			return motor.onTarget();
		}
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
}
