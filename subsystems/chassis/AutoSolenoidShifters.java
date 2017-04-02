package org.usfirst.frc4904.standard.subsystems.chassis;


import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutoSolenoidShifters extends SolenoidShifters {
	protected long lastManualShift;
	protected long lastAutoShift;

	public AutoSolenoidShifters(DoubleSolenoid solenoid) {
		super(solenoid);
	}

	public AutoSolenoidShifters(DoubleSolenoid solenoid, boolean isInverted) {
		super(solenoid, isInverted);
	}

	public AutoSolenoidShifters(int portUp, int portDown) {
		super(portUp, portDown);
	}

	@Override
	public void shift() {
		super.shift();
		lastManualShift = System.currentTimeMillis();
	}

	@Override
	public void shift(ShiftState state) {
		super.shift(state);
		lastManualShift = System.currentTimeMillis();
	}

	public void shiftAuto() {
		super.shift();
		lastAutoShift = System.currentTimeMillis();
	}

	public void shiftAuto(ShiftState state) {
		super.shift(state);
		lastAutoShift = System.currentTimeMillis();
	}

	public long timeSinceLastManualShift() {
		return System.currentTimeMillis() - lastManualShift;
	}

	public long timeSinceLastAutoShift() {
		return System.currentTimeMillis() - lastAutoShift;
	}
}
