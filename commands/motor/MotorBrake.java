package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.subsystems.motor.BrakeableMotor;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorBrake extends Command {
	protected final BrakeableMotor[] motors;

	public MotorBrake(String name, BrakeableMotor... motors) {
		super(name);
		this.motors = motors;
		for (BrakeableMotor motor : motors) {
			motor.setBrakeMode();
			if (motor instanceof Subsystem) {
				requires((Subsystem) motor);
			}
		}
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