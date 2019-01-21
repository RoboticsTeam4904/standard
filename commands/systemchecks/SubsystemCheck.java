package org.usfirst.frc4904.standard.commands.systemchecks;

import java.util.HashMap;
import java.util.Map;
import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SubsystemCheck extends Command {
    protected HashMap<String, StatusMessage> statuses;
    protected Subsystem[] systems;

    public SubsystemCheck (String name, Subsystem... systems) {
        super(name);
        this.systems = systems;
        for (Subsystem system : systems) {
            requires(system);
        }
    }

    public SubsystemCheck (Subsystem... systems) {
        this("SystemCheck", systems);
    }

    public void initStatuses() {
        for (Subsystem system : systems) {
            statuses.put(system.getName(), new StatusMessage(StatusMessage.SystemStatus.PASS, ""));
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
        for (Map.Entry<String, StatusMessage> entry : statuses.entrySet()) {
            String name = entry.getKey();
            StatusMessage message = entry.getValue();
            LogKitten.wtf("Subsystem: " + name + ", Status: " + (message.status == StatusMessage.SystemStatus.PASS ? "PASS" : "FAIL") + ", ERROR: " + message.errorMessage);
        }
    }

    
}