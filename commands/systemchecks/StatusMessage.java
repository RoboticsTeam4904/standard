package org.usfirst.frc4904.standard.commands.systemchecks;

public class StatusMessage {
    protected SystemStatus status;
    protected Exception[] exceptions;

    public StatusMessage(SystemStatus status, Exception... exceptions) {
        this.status = status;
        this.exceptions = exceptions;
    }
    
    public enum SystemStatus {
        PASS, FAIL;
    }
}