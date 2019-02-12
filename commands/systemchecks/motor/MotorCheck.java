package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.commands.systemchecks.SubsystemCheck;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;

public class MotorCheck extends SubsystemCheck {
	protected static final double DEFAULT_SPEED = 0.5; // TODO: CHECK THIS
	protected static final Util.Range outputCurrentRange = new Util.Range(0.1, 0.3); // TODO: Use Current to judge speedcontrollers
	protected double speed;
	protected final Motor[] motors;

	public MotorCheck(String name, double timeout, double speed, Motor... motors) {
		super(name, timeout, motors);
		this.motors = motors;
		this.speed = speed;
	}

	public MotorCheck(String name, double speed, Motor... motors) {
		this(name, DEFAULT_TIMEOUT, speed, motors);
	}

	public MotorCheck(double timeout, double speed, Motor... motors) {
		this("MotorCheck", timeout, speed, motors);
	}

	public MotorCheck(double speed, Motor... motors) {
		this(DEFAULT_TIMEOUT, speed, motors);
	}

	public MotorCheck(String name, Motor... motors) {
		this(name, DEFAULT_SPEED, motors);
	}

	public MotorCheck(Motor... motors) {
		this("MotorCheck", motors);
	}

	public void initialize() {
		for (Motor motor : motors) {
			motor.set(speed);
		}
	}

	public void execute() {
		for (Motor motor : motors) {
			try {
				motor.set(speed);
			}
			catch (Exception e) {
				updateStatusFail(motor.getName(), e);
			}
		}
	}
}