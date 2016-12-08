package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * MotorPositionConstant is a Command that runs while setting a SensorMotor's position
 * to the provided (double) value. If (boolean) endOnArrival is provided and set to false,
 * the command will run indefinitely. Otherwise, the command will end when the motor#onTarget()
 * returns true (as set by internal PID).
 *
 */
public class MotorPositionConstant extends Command {
	protected PositionSensorMotor motor;
	protected double position;
	protected boolean endOnArrival;
	
	public MotorPositionConstant(PositionSensorMotor motor, double position, boolean endOnArrival) {
		super("MotorPositionConstant");
		this.motor = motor;
		this.position = position;
		this.endOnArrival = endOnArrival;
		requires(motor);
		setInterruptible(true);
		LogKitten.wtf("CONSTRUCT");
	}
	
	public MotorPositionConstant(PositionSensorMotor motor, double position) {
		this(motor, position, true);
	}
	
	@Override
	protected void initialize() {
		motor.reset();
		motor.enableMC();
		motor.setPosition(position);
		LogKitten.wtf("INIT");
	}
	
	@Override
	protected void execute() {
		LogKitten.wtf(motor.get() + "");
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
	protected void interrupted() {
		LogKitten.wtf("INTERRUPTED");
	}
}
