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

public class TurnPIDCheck extends ChassisTurn implements Check {
    protected HashMap<String, StatusMessage> statuses;
    protected static final String CHECK_NAME = "TurnPIDCheck";
    protected final double finalAngle;
    protected static final double ERROR_THRESHOLD = 2.0; // TODO: CHANGE THIS
    protected double angle;

    public TurnPIDCheck(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
        super(chassis, finalAngle, imu, motionController);
        this.finalAngle = finalAngle;
        initStatuses();
    }

    @Override
    public void end() {
        try {
            angle = motionController.getInputSafely();
        }
        catch (InvalidSensorException e) {
            angle = 0;
            updateStatus(CHECK_NAME, SystemStatus.FAIL, e);
        }
        if (Math.abs(motionController.getSetpoint() - angle) > ERROR_THRESHOLD) {
            updateStatus(CHECK_NAME, SystemStatus.FAIL, new Exception("ANGLE TURNED NOT WITHIN ERROR THRESHOLD"));
        }
        move.cancel();
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
        LogKitten.wtf(CHECK_NAME + ": " + getStatusMessage(CHECK_NAME).exceptions); // TODO: change logkitten level
    }
}