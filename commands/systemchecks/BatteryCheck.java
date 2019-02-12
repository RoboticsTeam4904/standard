package org.usfirst.frc4904.standard.commands.systemchecks;


import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.hal.can.CANStatus;
import edu.wpi.first.wpilibj.RobotController;

/**
 * Systemcheck of battery status
 */
public class BatteryCheck extends BaseCheck {
	protected final double MIN_VOLTAGE = 9.0;
	protected CANStatus status;
	protected static final String systemName = "BATTERY";

	/**
	 * @param name
	 *                name of check
	 * @param timeout
	 *                duration of check
	 */
	public BatteryCheck(String name, double timeout) {
		super(name, timeout, systemName);
	}

	/**
	 * @param name
	 *                name of check
	 * @param timeout
	 *                duration of check
	 */
	public BatteryCheck(double timeout) {
		super("RobotControllerCheck", timeout);
	}

	/**
	 * @param name
	 *                name of check
	 * @param timeout
	 *                duration of check
	 */
	public BatteryCheck(String name) {
		super(name, DEFAULT_TIMEOUT);
	}

	public void execute() {
		status = RobotController.getCANStatus();
		if (RobotController.getBatteryVoltage() < MIN_VOLTAGE) {
			updateStatusFail(systemName, new Exception("BATTERY VOLTAGE LESS THAN MIN VOLTAGE REQUIREMENT"));
		}
		if (RobotController.isBrownedOut()) {
			updateStatusFail(systemName, new Exception("BROWNED OUT"));
		}
	}
}