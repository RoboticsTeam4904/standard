package org.usfirst.frc4904.cmdbased.commands.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisShift extends Command {
	private SolenoidShifters solenoids;
	private SolenoidShifters.ShiftState state;
	
	/**
	 * Shifts the solenoids to the opposite state
	 * 
	 * @param solenoids
	 */
	public ChassisShift(SolenoidShifters solenoids) {
		super("ChassisShift");
		this.solenoids = solenoids;
		requires(solenoids);
		setInterruptible(false);
		state = null;
	}
	
	/**
	 * Shifts the solenoids to the state state
	 * 
	 * @param solenoids
	 * @param state
	 */
	public ChassisShift(SolenoidShifters solenoids, SolenoidShifters.ShiftState state) {
		super("ChassisShift");
		this.solenoids = solenoids;
		requires(solenoids);
		setInterruptible(false);
		this.state = state;
	}
	
	protected void initialize() {
		switch (state) {
			case UP:
				solenoids.shift(SolenoidShifters.ShiftState.UP);
				return;
			case DOWN:
				solenoids.shift(SolenoidShifters.ShiftState.DOWN);
				return;
			default:
				solenoids.shift();
		}
	}
	
	protected void execute() {}
	
	protected void interrupted() {}
	
	protected void end() {}
	
	protected boolean isFinished() {
		return false;
	}
}
