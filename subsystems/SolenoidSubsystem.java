package org.usfirst.frc4904.standard.subsystems;


import org.usfirst.frc4904.standard.commands.solenoid.SolenoidSet;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SolenoidSubsystem extends Subsystem {
	protected DoubleSolenoid[] solenoids;
	protected SolenoidState state;
	protected SolenoidState defaultState;
	protected boolean isInverted;

	public SolenoidSubsystem(String name, boolean isInverted, SolenoidState defaultState, DoubleSolenoid... solenoids) {
		super(name);
		this.solenoids = solenoids;
		this.isInverted = isInverted;
		this.defaultState = defaultState;
		this.state = defaultState;
	}

	public SolenoidSubsystem(String name, boolean isInverted, DoubleSolenoid... solenoids) {
		this(name, isInverted, SolenoidState.OFF, solenoids);
	}

	public SolenoidSubsystem(String name, SolenoidState defaultState, DoubleSolenoid... solenoids) {
		this(name, false, defaultState, solenoids);
	}

	public SolenoidSubsystem(String name, DoubleSolenoid... solenoids) {
		this(name, false, solenoids);
	}

	public SolenoidSubsystem(SolenoidState defaultState, DoubleSolenoid... solenoids) {
		this("SolenoidSubsystem", defaultState, solenoids);
	}

	public SolenoidSubsystem(boolean isInverted, DoubleSolenoid... solenoids) {
		this("SolenoidSubsystem", isInverted, solenoids);
	}

	public SolenoidSubsystem(DoubleSolenoid... solenoids) {
		this("SolenoidSubsystem", solenoids);
	}

	public enum SolenoidState {
		OFF(DoubleSolenoid.Value.kOff), EXTEND(DoubleSolenoid.Value.kForward), RETRACT(DoubleSolenoid.Value.kReverse);
		public final DoubleSolenoid.Value value;

		private SolenoidState(DoubleSolenoid.Value value) {
			this.value = value;
		}
	}

	public void setState(SolenoidState state) {
		this.state = state;
	}

	public SolenoidState getState() {
		return state;
	}

	public SolenoidState invertState(SolenoidState state) {
		switch (state) {
			case EXTEND:
				return SolenoidState.RETRACT;
			case RETRACT:
				return SolenoidState.EXTEND;
		}
		return state;
	}

	public void set(SolenoidState state) {
		if (isInverted) {
			state = invertState(state);
		}
		if (this.state != state) {
			this.state = state;
			for (DoubleSolenoid solenoid : solenoids) {
				solenoid.set(state.value);
			}
		}
	}

	public void setOverride(SolenoidState state) {
		if (isInverted) {
			state = invertState(state);
		}
		for (DoubleSolenoid solenoid : solenoids) {
			solenoid.set(state.value);
		}
	}

	public DoubleSolenoid[] getSolenoids() {
		return solenoids;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new SolenoidSet(this, defaultState));
	}
}
