package org.usfirst.frc4904.standard.custom.sensors;


public class InvalidSensorException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -8211283541364995438L;

	public InvalidSensorException() {
		super();
	}
	
	public InvalidSensorException(String message) {
		super(message);
	}

	public InvalidSensorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSensorException(Throwable cause) {
		super(cause);
	}
}
