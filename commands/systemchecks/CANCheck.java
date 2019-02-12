package org.usfirst.frc4904.standard.commands.systemchecks;


import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.hal.can.CANStatus;
import edu.wpi.first.wpilibj.RobotController;

/**
 * System check of CAN
 */
public class CANCheck extends BaseCheck {
	protected final int MAX_ERROR_COUNT = 127;
	protected final int MAX_OFF_COUNT = 0;
	protected static final String systemName = "CAN";
	protected CANStatus status;

	/**
	 * @param name
	 *                name of check
	 * @param timeout
	 *                duration of check
	 */
	public CANCheck(String name, double timeout) {
		super(name, timeout, systemName);
	}

	/**
	 * @param timeout
	 *                duration of check
	 */
	public CANCheck(double timeout) {
		super("CANCheck", timeout);
	}

	/**
	 * @param name
	 *             name of check
	 */
	public CANCheck(String name) {
		super(name, DEFAULT_TIMEOUT);
	}

	public void execute() {
		status = RobotController.getCANStatus();
		if (status.receiveErrorCount > MAX_ERROR_COUNT) {
			updateStatusFail(systemName, new Exception("TOO MANY RECEIVE ERRORS"));
		}
		if (status.transmitErrorCount > MAX_ERROR_COUNT) {
			updateStatusFail(systemName, new Exception("TOO MANY TRANSMISSION ERRORS"));
		}
		if (status.busOffCount > MAX_OFF_COUNT) {
			updateStatusFail(systemName, new Exception("TOO MANY CANBUS OFF OCCURANCES"));
		}
	}
}