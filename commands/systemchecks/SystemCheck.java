package org.usfirst.frc4904.standard.commands.systemchecks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;

public abstract class SystemCheck extends BasicCheck {
    protected HashMap<String, StatusMessage> statuses;
    protected SendableBase[] systems;

    public SystemCheck(String name, double timeout, SendableBase... systems) {
        super(name, timeout, Arrays.asList(systems).stream().map(system -> system.getName()).collect(Collectors.toList()).toArray(String[]::new));
        this.systems = systems;
    }

    public SystemCheck(double timeout, SendableBase... systems) {
        this("SystemCheck", timeout, systems);
    }

    public SystemCheck(String name, SendableBase... systems) {
        this(name, DEFAULT_TIMEOUT, systems);
    }
    
    public SystemCheck(SendableBase... systems) {
        this("SystemCheck", systems);
    }

    @Override
    public void initStatuses() {
        for (SendableBase system : systems) {
            initStatus(system.getName());
        }
    }
}