package org.usfirst.frc4904.standard.commands;

// import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class cancels a command
 */
public class Cancel extends Command {
	protected final Command command;

	public Cancel(Command command) {
		super();
		setName("Cancel " + command.getName());
		this.command = command;
	}

	public void initialize() {
		// LogKitten.v("Initializing " + getName());
		command.cancel();
	}

	public boolean isFinished() {
		return true;
	}
}
