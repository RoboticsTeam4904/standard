package org.usfirst.frc4904.standard.subsystems;


import org.usfirst.frc4904.standard.commands.solenoid.SolenoidExtend;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SolenoidSubsystem extends Subsystem {
	protected DoubleSolenoid[] solenoids;
	protected SolenoidState state;
	protected boolean isInverted;

	public SolenoidSubsystem(String name, boolean isInverted, DoubleSolenoid... solenoids) {
		super(name);
		this.solenoids = solenoids;
		this.state = SolenoidState.OFF;
		this.isInverted = isInverted;
	}

	public SolenoidSubsystem(String name, DoubleSolenoid... solenoids) {
		this(name, false, solenoids);
	}

	public SolenoidSubsystem(boolean isInverted, DoubleSolenoid... solenoids) {
		this("SolenoidSubsystem", isInverted, solenoids);
	}

	public SolenoidSubsystem(DoubleSolenoid... solenoids) {
		this("SolenoidSubsystem", solenoids);
	}

	public enum SolenoidState {
		OFF(DoubleSolenoid.Value.kOff), FORWARD(DoubleSolenoid.Value.kForward), REVERSE(DoubleSolenoid.Value.kReverse);
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
			case FORWARD:
				return SolenoidState.REVERSE;
			case REVERSE:
				return SolenoidState.FORWARD;
		}
		return state;
	}

	public void set(SolenoidState state) {
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
		setDefaultCommand(new SolenoidExtend(this));
	}
}
