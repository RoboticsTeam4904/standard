package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;

public interface Check {

    public void setStatus(String key, StatusMessage.SystemStatus status, Exception... exceptions);

    default void initStatus(String name) {
        setStatus(name, SystemStatus.PASS, new Exception("NO ERROR"));
    }

    public void initStatuses();

    public void updateStatus(String key, StatusMessage.SystemStatus status, Exception... exceptions);

    default void setStatusFail(String key, Exception... exceptions) {
        updateStatus(key, SystemStatus.FAIL, exceptions);
    }

    default void setStatusPass(String key, Exception... exceptions) {
        updateStatus(key, SystemStatus.PASS, exceptions);
    }

    public void outputStatuses();
}