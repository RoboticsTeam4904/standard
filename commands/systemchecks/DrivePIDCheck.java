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

public class DrivePIDCheck extends ChassisMoveDistance implements Check {
    protected HashMap<String, StatusMessage> statuses;
    protected static final String CHECK_NAME = "DrivePIDCheck";
    protected final double distance;
    protected static final double ERROR_THRESHOLD = 2.0;
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
            updateStatus(CHECK_NAME, SystemStatus.FAIL, e);
        }
        if (Math.abs(motionController.getSetpoint() - position) > ERROR_THRESHOLD) {
            updateStatus(CHECK_NAME, SystemStatus.FAIL, new Exception("DISTANCE DRIVEN NOT WITHIN ERROR THRESHOLD"));
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
            Stream.concat(Arrays.stream(getExceptions(key)), Arrays.stream(exceptions)).toArray(Exception[]::new));
    }

    public StatusMessage getStatusMessage(String key) {
        return statuses.get(key);
    }

    public Exception[] getExceptions(String key) {
        return getStatusMessage(key).exceptions;
    }

    public void outputStatuses() {
        LogKitten.wtf(CHECK_NAME + ": " + getExceptions(CHECK_NAME)); // TODO: change logkitten level
    }
}