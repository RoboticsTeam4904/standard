package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;

public class MotorCheck extends SubsystemCheck {
    protected static final double SET_SPEED = 0.5;
    protected static final Util.Range outputCurrentRange = new Util.Range(0.1, 0.3); // TODO: Use Current to judge speedcontrollers
    protected final Motor[] motors;

    public MotorCheck(String name, Motor... motors) {
        super(name, motors);
        this.motors = motors;
        
    }

    public MotorCheck(Motor... motors) {
        this("MotorCheck", motors);
    }

    public void initialize() {
        for (Motor motor: motors){
            motor.set(SET_SPEED);
        }
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