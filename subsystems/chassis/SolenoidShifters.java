package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.commands.chassis.ShiftersIdle;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SolenoidShifters extends Subsystem {
	private final Solenoid leftSolenoidUp;
	private final Solenoid leftSolenoidDown;
	private final Solenoid rightSolenoidUp;
	private final Solenoid rightSolenoidDown;
	private ShiftState state;
	
	public enum ShiftState {
		UP, DOWN;
	}
	
	public SolenoidShifters(Solenoid leftUp, Solenoid leftDown, Solenoid rightUp, Solenoid rightDown) {
		super("SolenoidShifters");
		this.leftSolenoidUp = leftUp;
		this.leftSolenoidDown = leftDown;
		this.rightSolenoidUp = rightUp;
		this.rightSolenoidDown = rightDown;
		if (leftSolenoidUp.get()) {
			state = ShiftState.UP;
		} else {
			state = ShiftState.DOWN;
		}
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ShiftersIdle(this));
	}
	
	public ShiftState getShiftState() {
		return state;
	}
	
	public void shift(ShiftState state) {
		switch (state) {
			case UP:
				leftSolenoidDown.set(false);
				rightSolenoidDown.set(false);
				leftSolenoidUp.set(true);
				rightSolenoidUp.set(true);
				return;
			case DOWN:
				leftSolenoidUp.set(false);
				rightSolenoidUp.set(false);
				leftSolenoidDown.set(true);
				rightSolenoidDown.set(true);
				return;
			default:
				return;
		}
	}
	
	public void shift() {
		switch (state) {
			case UP:
				shift(ShiftState.DOWN);
				return;
			case DOWN:
				shift(ShiftState.UP);
				return;
			default:
				return;
		}
	}
}
