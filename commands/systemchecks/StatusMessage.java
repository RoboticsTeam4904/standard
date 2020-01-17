package org.usfirst.frc4904.standard.commands.systemchecks;

/**
 * Status class that contains SystemStatus and Exceptions
 */
public class StatusMessage {
    protected SystemStatus status;
    protected Exception[] exceptions;

	/**
	 * @param status
	 * status of StatusMessage
	 * @param exceptions
	 * exceptions of StatusMessage
	 */
    public StatusMessage(SystemStatus status, Exception... exceptions) {
        this.status = status;
        this.exceptions = exceptions;
    }

    public enum SystemStatus {
        PASS, FAIL;
    }
}