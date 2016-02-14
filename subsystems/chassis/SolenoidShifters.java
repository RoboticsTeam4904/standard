package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.commands.chassis.ShiftersIdle;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem for storing a set of solenoids for a two-shifter drivetrain.
 *
 */
public class SolenoidShifters extends Subsystem {
	private final Solenoid leftSolenoidUp;
	private final Solenoid leftSolenoidDown;
	private final Solenoid rightSolenoidUp;
	private final Solenoid rightSolenoidDown;
	private ShiftState state;
	
	public enum ShiftState {
		UP, DOWN;
	}
	
	/**
	 * Creates a shiting subsystem
	 * 
	 * @param leftUp
	 *        :
	 *        The solenoid port to activate to shift up the left gearbox
	 * @param leftDown
	 *        :
	 *        The solenoid port to activate to shift down the left gearbox
	 * @param rightUp
	 *        :
	 *        The solenoid port to activate to shift up the right gearbox
	 * @param rightDown
	 *        :
	 *        The solenoid port to activate to shift down the right gearbox
	 */
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
	
	/**
	 * Returns the current state of the
	 * solenoid shifters. This is based
	 * on the set state, not a measured
	 * state.
	 */
	public ShiftState getShiftState() {
		return state;
	}
	
	/**
	 * Shifts both gearboxes to up state or down state
	 * 
	 * @param state
	 */
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
	
	/**
	 * Toggles current shift state for both gearboxes
	 */
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
