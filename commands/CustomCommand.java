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
        super();
        setName(name);
        addRequirements(requirements);
    }


}