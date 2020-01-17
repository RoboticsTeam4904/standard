package org.usfirst.frc4904.standard.commands.systemchecks;


import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;

public interface Check {
	/**
	 * Sets the status of a system, overriding previous exceptions
	 * 
	 * @param key
	 *                   name of system
	 * @param status
	 *                   PASS or FAIL
	 * @param exceptions
	 *                   exceptions caused by the system
	 */
	public void setStatus(String key, SystemStatus status, Exception... exceptions);

	/**
	 * Initializes the status of system to PASS
	 * 
	 * @param key
	 *            name of system
	 */
	default void initStatus(String key) {
		setStatus(key, SystemStatus.PASS);
	}

	/**
	 * Initializes statuses of all systems
	 */
	public void initStatuses();

	/**
	 * Sets the status of a system, preserving past exceptions
	 * 
	 * @param key
	 *                   name of system
	 * @param status
	 *                   PASS or FAIL
	 * @param exceptions
	 *                   exceptions caused by the system
	 */
	public void updateStatus(String key, SystemStatus status, Exception... exceptions);

	/**
	 * Sets the status of a system to FAIL
	 * 
	 * @param key
	 *                   name of system
	 * @param exceptions
	 *                   exceptions caused by the system
	 */
	default void updateStatusFail(String key, Exception... exceptions) {
		updateStatus(key, SystemStatus.FAIL, exceptions);
	}

	/**
	 * Sets the status of a system to PASS
	 * 
	 * @param key
	 *            name of system
	 */
	default void setStatusPass(String key) {
		setStatus(key, SystemStatus.PASS);
	}

	/**
	 * Outputs the statuses of all systems of check
	 */
	public void outputStatuses();
}