package org.usfirst.frc4904.standard.commands.systemchecks;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CheckGroup extends CommandGroup {
    public CheckGroup(String name, BasicCheck... checks) {
        for (BasicCheck check : checks) {
            addParallel(check);
        }
    }
}