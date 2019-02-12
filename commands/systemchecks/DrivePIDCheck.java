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
	protected HashMap<String, StatusMessage> statuses;
	protected static final String CHECK_NAME = "DrivePIDCheck";
	protected final double distance;
	protected static final double ERROR_THRESHOLD = 2.0; // TODO: CHANGE THIS
	protected double position;

	public DrivePIDCheck(Chassis chassis, double distance, MotionController motionController) {
		super(chassis, distance, motionController);
		this.distance = distance;
		initStatuses();
	}

	public void end() {
		try {
			position = motionController.getInputSafely();
		}
		catch (InvalidSensorException e) {
			position = 0;
			updateStatusFail(CHECK_NAME, e);
		}
		if (Math.abs(motionController.getSetpoint() - position) > ERROR_THRESHOLD) {
			updateStatusFail(CHECK_NAME, new Exception("DISTANCE DRIVEN NOT WITHIN ERROR THRESHOLD"));
		}
		chassisMove.cancel();
		motionController.disable();
		motionController.reset();
		runOnce = false;
	}

	public void initStatuses() {
		initStatus(CHECK_NAME);
	}

	public void setStatus(String name, SystemStatus status, Exception... exceptions) {
		statuses.put(name, new StatusMessage(status, exceptions));
	}

	public void updateStatus(String key, SystemStatus status, Exception... exceptions) {
		setStatus(key, status,
			Stream.concat(Arrays.stream(getStatusMessage(key).exceptions), Arrays.stream(exceptions))
				.toArray(Exception[]::new));
	}

	public StatusMessage getStatusMessage(String key) {
		return statuses.get(key);
	}

	public void outputStatuses() {
		if (getStatusMessage(CHECK_NAME).status == SystemStatus.PASS) {
			LogKitten.d(CHECK_NAME + ": PASS");
		} else {
			LogKitten.e(CHECK_NAME + ": " + getStatusMessage(CHECK_NAME).exceptions);
		}
	}
}