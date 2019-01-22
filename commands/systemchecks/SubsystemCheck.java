package org.usfirst.frc4904.standard.commands.systemchecks;

import java.util.HashMap;
import java.util.Map;
import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SubsystemCheck extends SystemCheck {
    protected HashMap<String, StatusMessage> statuses;

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