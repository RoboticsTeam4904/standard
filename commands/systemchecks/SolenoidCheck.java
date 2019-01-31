package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SolenoidCheck extends SystemCheck {
    protected final DoubleSolenoid[] solenoids;

    public SolenoidCheck(String name, DoubleSolenoid... solenoids) {
        super(name, solenoids);
        this.solenoids = solenoids;
    }

    public SolenoidCheck(DoubleSolenoid... solenoids) {
        this("SolenoidCheck", solenoids);
    }

    public void initialize() {
        for (DoubleSolenoid solenoid : solenoids) {
            try {
                solenoid.set(DoubleSolenoid.Value.kForward);   
            } catch (Exception e) {
                updateStatus(solenoid.getName(), SystemStatus.FAIL, e);
            }
        }
    }
}