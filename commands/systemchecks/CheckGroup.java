package org.usfirst.frc4904.standard.commands.systemchecks;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Runs a series of robot system checks
 */
public class CheckGroup extends CommandGroup {
	/**
	 * @param name
	 *               name of commandgroup
	 * @param checks
	 *               systemchecks to run
	 */
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