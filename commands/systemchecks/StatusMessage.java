package org.usfirst.frc4904.standard.commands.systemchecks;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;


public class StatusMessage {
    protected SystemStatus status;
    protected String errorMessage;
    // protected Exception e; // TODO: incorporate Exceptions

    public StatusMessage(SystemStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
    
    public enum SystemStatus {
        PASS, FAIL;
    }
}