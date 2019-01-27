package org.usfirst.frc4904.standard.commands.systemchecks;

import java.util.HashMap;
import java.util.Map;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.wpilibj.command.Command;

public abstract class BasicCheck extends Command {
    protected HashMap<String, StatusMessage> statuses;
    protected final String[] systemNames;
    protected static final double DEFAULT_TIMEOUT = 5;

    public BasicCheck(String checkName, double timeout, String... systemNames) {
        super(checkName, timeout);
        this.systemNames = systemNames;
        initStatuses();
    }

    public BasicCheck(double timeout, String... systemNames) {
        this("Check", timeout, systemNames);
    }

    public BasicCheck(String... systemNames) {
        this(DEFAULT_TIMEOUT, systemNames);
    }

    @Override
    public boolean isFinished() {
        return isTimedOut();
    }

    @Override
    public void end() {
        outputStatuses();
    }

    public void setStatus(String name, SystemStatus status, String errorMessage) {
        statuses.put(name, new StatusMessage(status, errorMessage));
    }

    public void initStatus(String name) {
        setStatus(name, SystemStatus.PASS, "");
    }
    public void initStatuses() {
        for (String name : systemNames) {
            initStatus(name);
        }
    }
    
    public void updateStatus(String key, StatusMessage.SystemStatus status, String errorMessage) {
        setStatus(key, status, statuses.get(key).errorMessage + errorMessage);
    }
    public StatusMessage getStatusMessage(String key) {
        return statuses.get(key);
    }

    public SystemStatus getStatus(String key) {
        return getStatusMessage(key).status;
    }

    public String getError(String key) {
        return getStatusMessage(key).errorMessage;
    }

    public void outputStatuses() {
        LogKitten.wtf(getName() + " Statuses:");
        for (Map.Entry<String, StatusMessage> entry : statuses.entrySet()) {
            String name = entry.getKey();
            StatusMessage message = entry.getValue();
            // TODO: Change Logkitten level if FAIL vs PASS
            LogKitten.wtf("Subsystem: " + name + ", Status: " + (message.status == StatusMessage.SystemStatus.PASS ? "PASS" : "FAIL") + ", ERROR: " + message.errorMessage);
        }
    }
}