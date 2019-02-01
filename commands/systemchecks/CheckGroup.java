package org.usfirst.frc4904.standard.commands.systemchecks;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class CheckGroup extends CommandGroup {
    public CheckGroup(String name, BasicCheck... checks) {
        for (BasicCheck check : checks) {
            if (check instanceof SubsystemCheck) { //TODO: Think of better logic for this
                addSequential(check);
            }
            else {
                addParallel(check);
            }
        }
    }
}