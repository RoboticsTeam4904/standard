package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorBrake extends CommandBase {
	protected final BrakeableMotor[] motors;

	public MotorBrake(String name, BrakeableMotor... motors) {
		super();
		setName(name);
		this.motors = motors;
		for (BrakeableMotor motor : motors) {
			motor.setBrakeMode();
			if (motor instanceof SubsystemBase) {
				addRequirements((SubsystemBase) motor);
			}
		}
	}

	public MotorBrake(BrakeableMotor... motors) {
		this("MotorBreak", motors);
	}
	
	public void execute() {
		for (BrakeableMotor motor : motors) {
			motor.neutralOutput();
		}
	}

	public boolean isFinished() {
		return false;
	}
}