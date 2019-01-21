package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

public class MotorCheck extends Command {
    protected final Motor[] motors;
    protected static final double SET_SPEED = 0.5;
    protected static final Util.Range outputCurrentRange = new Util.Range(0.1, 0.3); // TODO: Use Current to judge speedcontrollers
    protected HashMap<String, StatusMessage> statuses;

    public MotorCheck(String name, Motor... motors) {
        super(name);
        this.motors = motors;
    }

    public MotorCheck(Motor... motors) {
        super("MotorCheck");
        this.motors = motors;
    }

    public void initialize() {
        for (Motor motor: motors){
            motor.set(SET_SPEED);
        }
    }

    public void initStatuses() {
        for (Motor motor : motors) {
            statuses.put(motor.getName(), new StatusMessage(StatusMessage.SystemStatus.PASS, ""));
        }
    }

    public void updateStatus(String key, StatusMessage.SystemStatus status, String errorMessage) {
        statuses.put(key, new StatusMessage(status, errorMessage));
    }

    public StatusMessage getStatusMessage(String key) {
        return statuses.get(key);
    }

    public StatusMessage.SystemStatus getStatus(String key) {
        return getStatusMessage(key).status;
    }

    public String getError(String key) {
        return getStatusMessage(key).errorMessage;
    }

    public void outputStatuses() {
        LogKitten.wtf(getName() + " Statuses:");
        Set set = statuses.entrySet();
    }

    public void execute() {
        for (Motor motor: motors){
            try {
                motor.set(SET_SPEED);
            } catch (Exception e) {
                updateStatus(motor.getName(), StatusMessage.SystemStatus.FAIL, e.getMessage());
            }
        }
    }
    public boolean isFinished(){
        return false;
    }
}