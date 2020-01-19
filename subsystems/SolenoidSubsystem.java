package org.usfirst.frc4904.standard.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc4904.standard.commands.solenoid.SolenoidSet;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A class that wraps multiple DoubleSolenoid objects with subsystem
 * functionality. Allows for easy inversion and setting of default state of
 * solenoids
 */
public class SolenoidSubsystem extends SubsystemBase {
	protected DoubleSolenoid[] solenoids;
	protected SolenoidState state;
	protected SolenoidState defaultState;
	protected boolean isInverted;

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param name         Name of the subsystem
	 * @param isInverted   True if the solenoids should be inverted
	 * @param defaultState Set the default state of the SolenoidSystem
	 * @param solenoids    Double solenoids of the system
	 */
	public SolenoidSubsystem(String name, boolean isInverted, SolenoidState defaultState, DoubleSolenoid... solenoids) {
		super();
		setName(name);
		setDefaultCommand(new SolenoidSet(this, defaultState));
		this.solenoids = solenoids;
		this.isInverted = isInverted;
		this.defaultState = defaultState;
		this.state = defaultState;
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param isInverted   True if the solenoids should be inverted
	 * @param defaultState Set the default state of the SolenoidSystem
	 * @param solenoids    Double solenoids of the system
	 */
	public SolenoidSubsystem(boolean isInverted, SolenoidState defaultState, DoubleSolenoid... solenoids) {
		this("SolenoidSubsystem", isInverted, defaultState, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param isInverted True if the solenoids should be inverted
	 * @param solenoids  Double solenoids of the system
	 */
	public SolenoidSubsystem(boolean isInverted, DoubleSolenoid... solenoids) {
		this(isInverted, SolenoidState.OFF, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param defaultState Set the default state of the SolenoidSystem
	 * @param solenoids    Double solenoids of the system
	 */
	public SolenoidSubsystem(SolenoidState defaultState, DoubleSolenoid... solenoids) {
		this(false, defaultState, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param solenoids Double solenoids of the system
	 */
	public SolenoidSubsystem(DoubleSolenoid... solenoids) {
		this(false, solenoids);
	}

	/**
	 * DoubleSolenoid.Value simplified to three simple states
	 */
	public enum SolenoidState {
		OFF(DoubleSolenoid.Value.kOff), EXTEND(DoubleSolenoid.Value.kForward), RETRACT(DoubleSolenoid.Value.kReverse);

		public final DoubleSolenoid.Value value;

		private SolenoidState(DoubleSolenoid.Value value) {
			this.value = value;
		}
	}

	/**
	 * @param state Returns the current state of the system
	 */
	public SolenoidState getState() {
		return state;
	}

	/**
	 * Inverts the state given
	 * 
	 * @param state SolenoidState to be inverted
	 * @return SolenoidState Inverted state
	 * 
	 */
	public SolenoidState invertState(SolenoidState state) {
		switch (state) {
		case EXTEND:
			return SolenoidState.RETRACT;
		case RETRACT:
			return SolenoidState.EXTEND;
		case OFF:
			return SolenoidState.OFF;
		}
		return state;
	}

	/**
	 * Sets the state of the system Only sets if current state is not equal to state
	 * to be set
	 * 
	 * @param state State to set system
	 */
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

	/**
	 * Sets the state of the system regardless of current state
	 * 
	 * @param state State to set
	 */
	public void setOverride(SolenoidState state) {
		if (isInverted) {
			state = invertState(state);
		}
		this.state = state;
		for (DoubleSolenoid solenoid : solenoids) {
			solenoid.set(state.value);
		}
	}

	/**
	 * @return solenoids DoubleSolenoid objects of the system
	 */
	public DoubleSolenoid[] getSolenoids() {
		return solenoids;
	}

	/**
	 * Returns whether the solenoid is extended.
	 * 
	 * @return extended
	 */
	public boolean isExtended() {
		return solenoids[0].get() == SolenoidState.EXTEND.value;
	}
}
