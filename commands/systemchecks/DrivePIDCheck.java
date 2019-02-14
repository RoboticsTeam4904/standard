package org.usfirst.frc4904.standard.commands.systemchecks;


import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.chassis.ChassisMoveDistance;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;

/**
 * Checks the accuracy of DrivePID by comparing the position of robot to setpoint
 */
public class DrivePIDCheck extends ChassisMoveDistance implements Check {
	protected static final double DEFAULT_ERROR_THRESHOLD = 2.0; // TODO: Test this
	protected static final String CHECK_NAME = "DrivePIDCheck";
	protected HashMap<String, StatusMessage> statuses;
	protected final double distance;
	protected double position;
	protected final double errorThreshold;

	/**
	 * Checks the accuracy of DrivePID by comparing the position of robot to setpoint
	 * 
	 * @param chassis
	 *                         Chassis being tested
	 * @param distance
	 *                         Distance to drive forward
	 * @param motionController
	 *                         MotionController used for drive PID
	 * @param errorThreshold
	 *                         threshold for error of the command
	 */
	public DrivePIDCheck(Chassis chassis, double distance, MotionController motionController, double errorThreshold) {
		super(chassis, distance, motionController);
		this.distance = distance;
		this.errorThreshold = errorThreshold;
		initStatuses();
	}

	/**
	 * Checks the accuracy of DrivePID by comparing the position of robot to setpoint
	 * 
	 * @param chassis
	 *                         Chassis being tested
	 * @param distance
	 *                         Distance to drive forward
	 * @param motionController
	 *                         MotionController used for drive PID
	 */
	public DrivePIDCheck(Chassis chassis, double distance, MotionController motionController) {
		this(chassis, distance, motionController, DEFAULT_ERROR_THRESHOLD);
	}

	/**
	 * Check if driven distance within error threshold
	 */
	public void end() {
		try {
			position = motionController.getInputSafely();
		}
		catch (InvalidSensorException e) {
			position = 0;
			updateStatusFail(CHECK_NAME, e);
		}
		if (Math.abs(motionController.getSetpoint() - position) > errorThreshold) {
			updateStatusFail(CHECK_NAME, new Exception("DISTANCE DRIVEN NOT WITHIN ERROR THRESHOLD"));
		}
		chassisMove.cancel();
		motionController.disable();
		motionController.reset();
		runOnce = false;
		outputStatuses();
	}

	/**
	 * Initialize status of DrivePIDCheck
	 */
	public void initStatuses() {
		initStatus(CHECK_NAME);
	}

	/**
	 * Set status of check, overriding past exceptions
	 */
	public void setStatus(String name, SystemStatus status, Exception... exceptions) {
		statuses.put(name, new StatusMessage(status, exceptions));
	}

	/**
	 * Update status of check, saving past exceptions
	 */
	public void updateStatus(String key, SystemStatus status, Exception... exceptions) {
		setStatus(key, status,
			Stream.concat(Arrays.stream(getStatusMessage(key).exceptions), Arrays.stream(exceptions))
				.toArray(Exception[]::new));
	}

	/**
	 * Get status and exceptions of system
	 */
	public StatusMessage getStatusMessage(String key) {
		return statuses.get(key);
	}

	/**
	 * Log the status of the check
	 */
	public void outputStatuses() {
		if (getStatusMessage(CHECK_NAME).status == SystemStatus.PASS) {
			LogKitten.d(CHECK_NAME + ": PASS");
		} else {
			LogKitten.e(CHECK_NAME + ": FAIL ");
			for (Exception e : getStatusMessage(CHECK_NAME).exceptions) {
				LogKitten.e(e);
			}
		}
	}
}