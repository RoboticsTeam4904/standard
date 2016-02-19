package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.commands.chassis.ChassisShift;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem for managing a solenoid for a shifting drivetrain.
 *
 */
public class SolenoidShifters extends Subsystem {
	protected final DoubleSolenoid solenoid;
	protected final boolean isInverted;
	protected ShiftState state;
	
	public enum ShiftState {
		UP, DOWN;
	}
	
	/**
	 * A subsystem for managing a solenoid for a shifting drivetrain.
	 *
	 * @param solenoid
	 *        The DoubleSolenoid used to shift
	 * @param isInverted
	 *        To invert or not
	 */
	public SolenoidShifters(DoubleSolenoid solenoid, boolean isInverted) {
		super("SolenoidShifters");
		this.solenoid = solenoid;
		this.isInverted = isInverted;
		if (!isInverted && solenoid.get() == DoubleSolenoid.Value.kForward) {
			state = ShiftState.UP;
		} else {
			state = ShiftState.DOWN;
		}
	}
	
	/**
	 * A subsystem for managing a solenoid for a shifting drivetrain.
	 *
	 * @param solenoid
	 *        The DoubleSolenoid used to shift
	 */
	public SolenoidShifters(DoubleSolenoid solenoid) {
		this(solenoid, false);
	}
	
	/**
	 * A subsystem for managing a solenoid for a shifting drivetrain.
	 *
	 * @param portUp
	 *        The first port of the double solenoid
	 * @param portDown
	 *        The second port of the double solenoid
	 */
	public SolenoidShifters(int portUp, int portDown) {
		this(new DoubleSolenoid(portUp, portDown), false);
	}
	
	/**
	 * A subsystem for managing a solenoid for a shifting drivetrain.
	 *
	 * @param module
	 *        The ID of the PCM for the double solenoid
	 * @param portUp
	 *        The first port of the double solenoid
	 * @param portDown
	 *        The second port of the double solenoid
	 */
	public SolenoidShifters(int module, int portUp, int portDown) {
		this(new DoubleSolenoid(module, portUp, portDown), false);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ChassisShift(this, SolenoidShifters.ShiftState.DOWN));
	}
	
	/**
	 * Returns the current state of the solenoid shifters.
	 * This is based on the set state, not a measured state.
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
				if (!isInverted) {
					solenoid.set(DoubleSolenoid.Value.kForward);
				} else {
					solenoid.set(DoubleSolenoid.Value.kReverse);
				}
				return;
			case DOWN:
			default:
				if (!isInverted) {
					solenoid.set(DoubleSolenoid.Value.kReverse);
				} else {
					solenoid.set(DoubleSolenoid.Value.kForward);
				}
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
