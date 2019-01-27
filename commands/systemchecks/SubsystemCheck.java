package org.usfirst.frc4904.standard.commands.systemchecks;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SubsystemCheck extends SystemCheck {
    public SubsystemCheck (String name, Subsystem... subsystems) {
        super(name, subsystems);
        for (Subsystem system : subsystems) {
            requires(system);
        }
    }

    public SubsystemCheck (Subsystem... systems) {
        this("SystemCheck", systems);
    }
}