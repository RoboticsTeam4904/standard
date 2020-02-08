package org.usfirst.frc4904.standard.subsystems.chassis;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A class that wraps multiple DoubleSolenoid objects with subsystem
 * functionality. Allows for easy inversion and setting of default state of
 * solenoids
 */
public class SolenoidShifters extends SubsystemBase { // TODO: make solenoidshifters extend solenoidsubsystem
	protected DoubleSolenoid[] solenoids;
	protected SolenoidState state;
	protected SolenoidState defaultState;
	protected boolean isInverted;

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param name         Name of subsystem
	 * @param isInverted   True if the solenoids should be inverted
	 * @param defaultState Set the default state of the SolenoidSystem
	 * @param solenoids    Double solenoids of the system
	 */
	public SolenoidShifters(String name, boolean isInverted, SolenoidState defaultState, DoubleSolenoid... solenoids) {
		setName(name);
		this.solenoids = solenoids;
		this.isInverted = isInverted;
		this.defaultState = defaultState;
		this.state = defaultState;
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoidss
	 * 
	 * @param name       Name of subsystem
	 * @param isInverted True if the solenoids should be inverted
	 * @param solenoids  Double solenoids of the system
	 */
	public SolenoidShifters(String name, boolean isInverted, DoubleSolenoid... solenoids) {
		this(name, isInverted, SolenoidState.RETRACT, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param name         Name of subsystem
	 * @param defaultState Set the default state of the SolenoidSystem
	 * @param solenoids    Double solenoids of the system
	 */
	public SolenoidShifters(String name, SolenoidState defaultState, DoubleSolenoid... solenoids) {
		this(name, false, defaultState, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param name      Name of subsystem
	 * @param solenoids Double solenoids of the system
	 */
	public SolenoidShifters(String name, DoubleSolenoid... solenoids) {
		this(name, false, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param defaultState Set the default state of the SolenoidSystem
	 * @param solenoids    Double solenoids of the system
	 */
	public SolenoidShifters(SolenoidState defaultState, DoubleSolenoid... solenoids) {
		this("SolenoidShifters", defaultState, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param isInverted True if the solenoids should be inverted
	 * @param solenoids  Double solenoids of the system
	 */
	public SolenoidShifters(boolean isInverted, DoubleSolenoid... solenoids) {
		this("SolenoidShifters", isInverted, solenoids);
	}

	/**
	 * A class that wraps multiple DoubleSolenoid objects with subsystem
	 * functionality. Allows for easy inversion and setting of default state of
	 * solenoids
	 * 
	 * @param solenoids Double solenoids of the system
	 */
	public SolenoidShifters(DoubleSolenoid... solenoids) {
		this("SolenoidShifters", solenoids);
	}

	/**
	 * DoubleSolenoid.Value simplified to three simple states
	 */
	public enum SolenoidState {
		EXTEND(DoubleSolenoid.Value.kForward), RETRACT(DoubleSolenoid.Value.kReverse);

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
		this.state = state;
		if (this.state != state) {
			for (DoubleSolenoid solenoid : solenoids) {
				solenoid.set(state.value);
			}
		}
	}

	/**
	 * Sets the state of the system Only sets if current state is not equal to state
	 * to be set
	 * 
	 * This implementation takes no parameters, as it just reverses the current
	 * state TODO: this may need to be changed
	 * 
	 * @throws Exception if the solenoid state currently is off
	 */
	public void set() { // TODO: consider OFF case
		if (state == SolenoidState.RETRACT) {
			set(SolenoidState.EXTEND);
		} else if (state == SolenoidState.EXTEND) {
			set(SolenoidState.RETRACT);
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
