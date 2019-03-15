package org.usfirst.frc4904.standard.subsystems;


import org.usfirst.frc4904.standard.commands.solenoid.SolenoidSet;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
 * but isn't actually connected to a solenoid.
 */
public class FakeSolenoidSubsystem extends SolenoidSubsystem {
	protected DoubleSolenoid[] solenoids;
	protected SolenoidState state;
	protected SolenoidState defaultState;
	protected boolean isInverted;

	/**
	 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
	 * but isn't actually connected to a solenoid.
	 *
	 * @param name
	 *                     Name of subsystem
	 * @param isInverted
	 *                     True if the solenoids should be inverted
	 * @param defaultState
	 *                     Set the default state of the SolenoidSystem
	 */
	public FakeSolenoidSubsystem(String name, boolean isInverted, SolenoidState defaultState) {
		super(name, isInverted, defaultState, new DoubleSolenoid[0]);
	}

	/**
	 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
	 * but isn't actually connected to a solenoid.
	 *
	 * @param name
	 *                   Name of subsystem
	 * @param isInverted
	 *                   True if the solenoids should be inverted
	 */
	public FakeSolenoidSubsystem(String name, boolean isInverted) {
		super(name, isInverted, new DoubleSolenoid[0]);
	}

	/**
	 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
	 * but isn't actually connected to a solenoid.
	 *
	 * @param name
	 *                     Name of subsystem
	 * @param defaultState
	 *                     Set the default state of the SolenoidSystem
	 */
	public FakeSolenoidSubsystem(String name, SolenoidState defaultState) {
		super(name, defaultState, new DoubleSolenoid[0]);
	}

	/**
	 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
	 * but isn't actually connected to a solenoid.
	 *
	 * @param name
	 *                  Name of subsystem
	 */
	public FakeSolenoidSubsystem(String name) {
		super(name, new DoubleSolenoid[0]);
	}

	/**
	 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
	 * but isn't actually connected to a solenoid.
	 *
	 * @param defaultState
	 *                     Set the default state of the SolenoidSystem
	 */
	public FakeSolenoidSubsystem(SolenoidState defaultState) {
		super(defaultState, new DoubleSolenoid[0]);
	}

	/**
	 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
	 * but isn't actually connected to a solenoid.
	 *
	 * @param isInverted
	 *                   True if the solenoids should be inverted
	 */
	public FakeSolenoidSubsystem(boolean isInverted) {
		super(isInverted, new DoubleSolenoid[0]);
	}

	/**
	 * A class that spoofs a SolenoidSubsystem and can be used for all SolenoidSubsystem commands, constructors, and methods,
	 * but isn't actually connected to a solenoid.
	 *
	 */
	public FakeSolenoidSubsystem() {
		super(new DoubleSolenoid[0]);
	}

	/**
	 * @param state
	 *              Returns the current state of the system
	 */
	public SolenoidState getState() {
		return state;
	}

	/**
	 * Sets the state of the system
	 * Only sets if current state is not equal to state to be set
	 * 
	 * @param state
	 *              State to set system
	 */
	public void set(SolenoidState state) {
		if (isInverted) {
			state = invertState(state);
		}
		if (this.state != state) {
			this.state = state;
			// for (DoubleSolenoid solenoid : solenoids) {
			// 	solenoid.set(state.value);
			// }
		}
	}

	/**
	 * Sets the state of the system regardless of current state
	 * 
	 * @param state
	 *              State to set
	 */
	public void setOverride(SolenoidState state) {
		if (isInverted) {
			state = invertState(state);
		}
		this.state = state;
		// for (DoubleSolenoid solenoid : solenoids) {
		// 	solenoid.set(state.value);
		// }
	}
}
