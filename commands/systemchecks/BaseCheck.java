package org.usfirst.frc4904.standard.commands.systemchecks;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Arrays;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.wpilibj.command.Command;

/**
 * BaseCheck is the base class for system checks.
 * Creates a hashmap between systems and their respective statuses
 * that can be updated depending on the results of system checks.
 */
public abstract class BaseCheck extends Command implements Check {
	protected static final double DEFAULT_TIMEOUT = 5;
	protected HashMap<String, StatusMessage> statuses;
	protected final String[] systemNames;

	/**
	 * @param checkName
	 *                    The name of the command
	 * @param timeout
	 *                    Duration of the command
	 * @param systemNames
	 *                    Names of the systems being checked
	 */
	public BaseCheck(String checkName, double timeout, String... systemNames) {
		super(checkName, timeout);
		this.systemNames = systemNames;
		initStatuses();
	}

	/**
	 * @param timeout
	 *                    Duration of the command
	 * @param systemNames
	 *                    Names of the systems being checked
	 */
	public BaseCheck(double timeout, String... systemNames) {
		this("Check", timeout, systemNames);
	}

	/**
	 * @param systemNames
	 *                    Names of the systems being checked
	 */
	public BaseCheck(String... systemNames) {
		this(DEFAULT_TIMEOUT, systemNames);
	}

	@Override
	public boolean isFinished() {
		return isTimedOut();
	}

	@Override
	public void end() {
		outputStatuses();
	}

	/**
	 * Sets status of a system given:
	 * 
	 * @param key
	 *                   name of the system
	 * @param status
	 *                   pass or fail status
	 * @param exceptions
	 *                   any exception that the class causes
	 * 
	 *                   Status should be SystemStatus.FAIL if exceptions given.
	 */
	public void setStatus(String key, SystemStatus status, Exception... exceptions) {
		statuses.put(key, new StatusMessage(status, exceptions));
	}

	/**
	 * Initializes a name-status pair to have a PASS status and no exceptions
	 * 
	 * @param key
	 *             name of the system
	 */
	public void initStatus(String key) {
		setStatus(key, SystemStatus.PASS);
	}

	/**
	 * Initializes all systems in systemNames using initStatus
	 */
	public void initStatuses() {
		for (String name : systemNames) {
			initStatus(name);
		}
	}

	/**
	 * Updates the status of a name-status pair by adding on exceptions
	 * 
	 * @param name
	 *                   name of the system
	 * @param status
	 *                   pass or fail status
	 * @param exceptions
	 *                   any exception that the class causes
	 */
	public void updateStatus(String key, SystemStatus status, Exception... exceptions) {
		setStatus(key, status, Stream.concat(Arrays.stream(getStatusMessage(key).exceptions), Arrays.stream(exceptions))
			.toArray(Exception[]::new));
	}

	/**
	 * Gets the StatusMessage (SystemStatus + exceptions) of a system given its name
	 * 
	 * @param key
	 *            name of system
	 * @return StatusMessage of system
	 */
	public StatusMessage getStatusMessage(String key) {
		return statuses.get(key);
	}

	/**
	 * Logs the statuses of all systems with variable severity
	 * depending on the status of the system
	 */
	public void outputStatuses() {
		LogKitten.d(getName() + " Statuses:");
		for (Map.Entry<String, StatusMessage> entry : statuses.entrySet()) {
			String name = entry.getKey();
			StatusMessage message = entry.getValue();
			if (message.status == SystemStatus.PASS) {
				LogKitten.d("Subsystem: " + name + ", Status: PASS");
			} else {
				LogKitten.wtf("Subsystem: " + name + ", Status: FAIL");
				for (Exception e : message.exceptions) {
					LogKitten.wtf(e);
				}
			}
		}
	}
}