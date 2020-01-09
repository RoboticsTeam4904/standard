package org.usfirst.frc4904.standard.subsystems;

import org.usfirst.frc4904.standard.commands.Noop;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;


public class CustomSubsystem extends SubsystemBase {
    public CustomSubsystem(String name, Command defaultCommand) {
        setName(name);
        setDefaultCommand(defaultCommand);
    }

    public CustomSubsystem(String name) {
        this(name, new Noop().perpetually());
    }
}