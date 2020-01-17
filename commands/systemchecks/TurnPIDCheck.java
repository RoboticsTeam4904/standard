package org.usfirst.frc4904.standard.commands.systemchecks;


import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.chassis.ChassisTurn;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;

/**
 * Checks accuracy of TurnPID by comparing angle to setpoint
 */
public class TurnPIDCheck extends ChassisTurn implements Check {
	protected HashMap<String, StatusMessage> statuses;
	protected static final String CHECK_NAME = "TurnPIDCheck";
	protected final double finalAngle;
	protected static final double DEFAULT_THRESHOLD = 2.0; // TODO: Test
	protected final double errorThreshold;
	protected double angle;

	/**
	 * Checks accuracy of TurnPID by comparing angle to setpoint
	 * 
	 * @param chassis
	 *                         Chassis to test
	 * @param finalAngle
	 *                         angle to turn
	 * @param imu
	 *                         IMU used for turning
	 * @param motioncontroller
	 *                         MotionController for turning PID
	 * @param errorThreshold
	 *                         threshold for error of command
	 */
	public TurnPIDCheck(Chassis chassis, double finalAngle, IMU imu, MotionController motionController, double errorThreshold) {
		super(chassis, finalAngle, imu, motionController);
		this.finalAngle = finalAngle;
		this.errorThreshold = errorThreshold;
		initStatuses();
	}

	/**
	 * Checks accuracy of TurnPID by comparing angle to setpoint
	 * 
	 * @param chassis
	 *                         Chassis to test
	 * @param finalAngle
	 *                         angle to turn
	 * @param imu
	 *                         IMU used for turning
	 * @param motioncontroller
	 *                         MotionController for turning PID
	 */
	public TurnPIDCheck(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
		this(chassis, finalAngle, imu, motionController, DEFAULT_THRESHOLD);
	}

	/**
	 * Check if turned angle within error threshold
	 */
	@Override
	public void end() {
		try {
			angle = motionController.getInputSafely();
		}
		catch (InvalidSensorException e) {
			angle = 0;
			updateStatusFail(CHECK_NAME, e);
		}
		if (Math.abs(motionController.getSetpoint() - angle) > errorThreshold) {
			updateStatusFail(CHECK_NAME, new Exception("ANGLE TURNED NOT WITHIN ERROR THRESHOLD"));
		}
		move.cancel();
		motionController.disable();
		motionController.reset();
		runOnce = false;
		outputStatuses();
	}

	/**
	 * Initialize status of check
	 */
	public void initStatuses() {
		initStatus(CHECK_NAME);
	}

	/**
	 * Set status of system, overriding past exceptions
	 */
	public void setStatus(String name, SystemStatus status, Exception... exceptions) {
		statuses.put(name, new StatusMessage(status, exceptions));
	}

	/**
	 * Update status of system, saving past exceptions
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
	 * Log status of system
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