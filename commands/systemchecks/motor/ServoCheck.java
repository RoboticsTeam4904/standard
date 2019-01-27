package org.usfirst.frc4904.standard.commands.systemchecks.motor;

import org.usfirst.frc4904.standard.commands.systemchecks.SubsystemCheck;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import org.usfirst.frc4904.standard.subsystems.motor.ServoSubsystem;

public class ServoCheck extends SubsystemCheck {
    protected final double angle;
    protected static final double DEFAULT_ANGLE = 50;
    protected final ServoSubsystem[] servos;

    public ServoCheck(String name, double angle, ServoSubsystem... servos) {
        super(name, servos);
        this.servos = servos;
        this.angle = angle;
    }

    public ServoCheck(String name, ServoSubsystem...servos) {
        this(name, DEFAULT_ANGLE, servos);
    }

    public ServoCheck(double angle, ServoSubsystem... servos) {
        this("ServoCheck", angle, servos);
    }

    public ServoCheck(ServoSubsystem... servos) {
        this("ServoCheck", servos);
    }

    public void initialize() {
        for (ServoSubsystem servo : servos) {
            try {
                servo.setAngle(angle);    
            } catch (Exception e) {
                updateStatus(servo.getName(), SystemStatus.FAIL, e.getMessage());
            }
        }
    }
}