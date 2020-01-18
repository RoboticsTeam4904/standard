package org.usfirst.frc4904.standard.custom;

/**
 * Designed to be a replacement for CANMessageNotFoundException that is not a
 * runtime exception. This allows us to avoid accidentally having robot code die
 * due to not noticing the runtime exception being thrown.
 */
public class CANMessageUnavailableException extends Exception {
	private static final long serialVersionUID = 8066629002370614378L;

	public CANMessageUnavailableException() {
		super();
	}

	public CANMessageUnavailableException(Throwable cause) {
		super(cause);
	}

	public CANMessageUnavailableException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CANMessageUnavailableException(String msg) {
		super(msg);
	}
}
