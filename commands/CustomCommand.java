package org.usfirst.frc4904.standard.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;


public abstract class CustomCommand extends CommandBase {
    public CustomCommand(String name, double timeout, Subsystem... requirements) {
        this(name, requirements);
        this.withTimeout(timeout);
        
    }

    public CustomCommand(String name, Subsystem... requirements) {
        this(name);
        addRequirements(requirements);
    }

    public CustomCommand(String name) {
        super();
        setName(name);
    }

    // TODO: write this well (so that it actually returns if something is running)
    /**
     * Check if a command is scheduled to run.
     * 
     * @return Whether the command is scheduled to run
     */
    public boolean isRunning() {
        return isScheduled();
    }
}