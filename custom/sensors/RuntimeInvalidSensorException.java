package org.usfirst.frc4904.standard.custom.sensors;


public class RuntimeInvalidSensorException extends RuntimeException {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7957035358617551883L;
	
	public RuntimeInvalidSensorException() {
		super();
	}

	public RuntimeInvalidSensorException(String message) {
		super(message);
	}
	
	public RuntimeInvalidSensorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RuntimeInvalidSensorException(Throwable cause) {
		super(cause);
	}
}
