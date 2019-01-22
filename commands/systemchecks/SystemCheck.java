package org.usfirst.frc4904.standard.commands.systemchecks;

import java.util.HashMap;
import java.util.Map;
import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.command.Command;

public abstract class SystemCheck extends Command {
    protected HashMap<String, StatusMessage> statuses;
    protected SendableBase[] systems;

    public SystemCheck(String name, SendableBase... systems) {
        super(name);
        this.systems = systems;
    }
    
    public SystemCheck(SendableBase... systems) {
        this("SystemCheck", systems);
    }

    public boolean isFinished() {
        return true;
    }
    public void initStatuses() {
        for (SendableBase system : systems) {
            statuses.put(system.getName(), new StatusMessage(StatusMessage.SystemStatus.PASS, ""));
        }
    }

    public void reassignStatus(String key, StatusMessage.SystemStatus status, String errorMessage) {
        statuses.put(key, new StatusMessage(status, errorMessage));
    }

    public void updateStatus(String key, StatusMessage.SystemStatus status, String errorMessage) {
        reassignStatus(key, status, statuses.get(key).errorMessage + errorMessage);
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
        for (Map.Entry<String, StatusMessage> entry : statuses.entrySet()) {
            String name = entry.getKey();
            StatusMessage message = entry.getValue();
            // TODO: Change Logkitten level if FAIL vs PASS
            LogKitten.wtf("Subsystem: " + name + ", Status: " + (message.status == StatusMessage.SystemStatus.PASS ? "PASS" : "FAIL") + ", ERROR: " + message.errorMessage);
        }
    }
}