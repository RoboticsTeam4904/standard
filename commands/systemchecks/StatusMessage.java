package org.usfirst.frc4904.standard.commands.systemchecks;

public class StatusMessage {
    protected SystemStatus status;
    protected String errorMessage;

    public StatusMessage(SystemStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
    
    public enum SystemStatus {
        PASS, FAIL;
    }
}