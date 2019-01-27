package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;

public interface Check {

    public void setStatus(String key, StatusMessage.SystemStatus status, String errorMessage);

    default void initStatus(String name) {
        setStatus(name, SystemStatus.PASS, "");
    }

    public void initStatuses();

    public void updateStatus(String key, StatusMessage.SystemStatus status, String errorMessage);

    default void setStatusFail(String key, String errorMessage) {
        updateStatus(key, SystemStatus.FAIL, errorMessage);
    }

    default void setStatusPass(String key, String errorMessage) {
        updateStatus(key, SystemStatus.PASS, errorMessage);
    }

    public void outputStatuses();
}