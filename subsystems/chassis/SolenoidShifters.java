package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * A class that wraps multiple DoubleSolenoid objects with subsystem
 * functionality. Allows for easy inversion and setting of default state of
 * solenoids
 */
public class SolenoidShifters extends SolenoidSubsystem { // TODO: make solenoidshifters extend solenoidsubsystem

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
		super(name, isInverted, defaultState, solenoids);
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
}
