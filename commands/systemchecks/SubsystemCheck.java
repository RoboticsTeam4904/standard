package org.usfirst.frc4904.standard.commands.systemchecks;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SubsystemCheck extends SystemCheck {
    public SubsystemCheck (String name, double timeout, Subsystem... subsystems) {
        super(name, timeout, subsystems);
        for (Subsystem system : subsystems) {
            requires(system);
        }
    }

    public SubsystemCheck(String name, Subsystem... systems) {
        this(name, DEFAULT_TIMEOUT, systems);
    }

    public SubsystemCheck(double timeout, Subsystem... systems) {
        this("SubsystemCheck", timeout, systems);
    }

    public SubsystemCheck(Subsystem... systems) {
        this("SubsystemCheck", systems);
    }
}