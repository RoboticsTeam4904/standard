package org.usfirst.frc4904.standard.commands.systemchecks;


import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * System Check of solenoids
 */
public class SolenoidCheck extends SystemCheck {
	protected final DoubleSolenoid[] solenoids;

	/**
	 * @param name
	 *                  name of check
	 * @param timeout
	 *                  duration of check
	 * @param solenoids
	 *                  list of solenoids
	 */
	public SolenoidCheck(String name, double timeout, DoubleSolenoid... solenoids) {
		super(name, timeout, solenoids);
		this.solenoids = solenoids;
	}

	/**
	 * @param name
	 *                  name of check
	 * @param solenoids
	 *                  list of solenoids
	 */
	public SolenoidCheck(String name, DoubleSolenoid... solenoids) {
		this(name, DEFAULT_TIMEOUT, solenoids);
	}

	/**
	 * @param timeout
	 *                  duration of check
	 * @param solenoids
	 *                  list of solenoids
	 */
	public SolenoidCheck(double timeout, DoubleSolenoid... solenoids) {
		this("SolenoidCheck", timeout, solenoids);
	}

	/**
	 * @param solenoids
	 *                  list of solenoids
	 */
	public SolenoidCheck(DoubleSolenoid... solenoids) {
		this("SolenoidCheck", solenoids);
	}

	/**
	 * Sets every solenoid to FORWARD and updates status if this fails
	 */
	public void initialize() {
		for (DoubleSolenoid solenoid : solenoids) {
			try {
				solenoid.set(DoubleSolenoid.Value.kForward);
			}
			catch (Exception e) {
				updateStatusFail(solenoid.getName(), e);
			}
		}
	}
}