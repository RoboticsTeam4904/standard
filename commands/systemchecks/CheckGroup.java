package org.usfirst.frc4904.standard.commands.systemchecks;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CheckGroup extends CommandGroup {
	public CheckGroup(String name, Check... checks) {
		for (Check check : checks) {
			if (check instanceof Command) {
				if (check instanceof SubsystemCheck) { // TODO: Think of better logic for this
					addSequential((SubsystemCheck) check);
				} else {
					addParallel((Command) check);
				}
			}
		}
	}
}