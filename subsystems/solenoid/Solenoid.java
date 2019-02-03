package org.usfirst.frc4904.standard.subsystems.solenoid;

import standard.commands.solenoid.SolenoidSet;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
public class Solenoid extends Subsystem{
    public enum State {
        Off,
        Forward,
        Reverse
      }
    protected DoubleSolenoid[] solenoids;
    public Solenoid(DoubleSolenoid... solenoids){
        this.solenoids = solenoids;
    }

    public void extendAll(){
        for(DoubleSolenoid solenoid:solenoids){
            solenoid.set(DoubleSolenoid.Value.kForward);
        }
        
    }
    public void retractAll(){
        for(DoubleSolenoid solenoid:solenoids){
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }
    public void allOff(){
        for(DoubleSolenoid solenoid:solenoids){
            solenoid.set(DoubleSolenoid.Value.kOff);
        }
    }
    public DoubleSolenoid[] getSolenoids(){
        return solenoids;
    }

    public void initDefaultCommand(){
        new SolenoidSet(this).set(Off);
    }
}