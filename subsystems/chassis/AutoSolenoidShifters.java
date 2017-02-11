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

	public void shift(ShiftState state, boolean isAutoShift) {
		if (isAutoShift) {
			lastAutoShift = System.currentTimeMillis();
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
	}

	public long timeSinceLastManualShift() {
		return System.currentTimeMillis() - lastManualShift;
	}

	public long timeSinceLastAutoShift() {
		return System.currentTimeMillis() - lastAutoShift;
	}
}
