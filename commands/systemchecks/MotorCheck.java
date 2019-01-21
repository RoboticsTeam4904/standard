package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Command;

public class MotorCheck extends Command {
    protected final Motor[] motors;
    protected static final double SET_SPEED = 0.5;
    protected static final Util.Range outCurrentRange = new Util.Range(0.1, 0.3); // TODO: Adjust this range
    // protected 

    public MotorCheck(Motor... motors) {
        this.motors = motors;
    }

    public void initialize() {
        for (Motor motor: motors){
            motor.set(SET_SPEED);
        }
    }

    public 

    public void execute() {
        for (Motor motor: motors){
            try {
                motor.set(SET_SPEED);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }
    public boolean isFinished(){
        return false;
    }
}