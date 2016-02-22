package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import edu.wpi.first.wpilibj.command.Command;

public class MotorPositionConstant extends Command {
	protected SensorMotor motor;
	protected double position;
	protected boolean endOnArrival;
	
	public MotorPositionConstant(SensorMotor motor, double position, boolean endOnArrival) {
		super("MotorPositionConstant");
		this.motor = motor;
		this.position = position;
		this.endOnArrival = endOnArrival;
		requires(motor);
		motor.enablePID();
		setInterruptible(true);
	}
	
	public MotorPositionConstant(SensorMotor motor, double position) {
		this(motor, position, true);
	}
	
	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {
		motor.setPosition(position);
	}
	
	@Override
	protected boolean isFinished() {
		if (endOnArrival) {
			return motor.onTarget();
		}
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
}
