package org.usfirst.frc4904.standard.commands.healthchecks;


import java.util.ArrayList;
import java.util.HashMap;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.TimedCommand;

public abstract class AbstractHealthCheck extends TimedCommand { // Many of our healthchecks will use timing, so a TimedCommand is needed
	protected HashMap<HealthLevel, ArrayList<HealthProtectCommand>> commands;
	protected volatile HealthLevel status;
	
	public AbstractHealthCheck(String name) {
		super(name);
		status = HealthLevel.UNKNOWN;
		setRunWhenDisabled(true);
		commands = new HashMap<HealthLevel, ArrayList<HealthProtectCommand>>();
	}
	
	public void runCommandOnState(HealthLevel level, HealthProtectCommand toRun) {
		if (commands.get(level) == null) {
			commands.put(level, new ArrayList<HealthProtectCommand>());
		}
		commands.get(level).add(toRun);
	}
	
	public void reset() {
		for (HealthLevel level : commands.keySet()) {
			if (commands.get(level) != null) {
				for (HealthProtectCommand hpc : commands.get(level)) {
					if (hpc.isRunning()) {
						hpc.reset();
					}
				}
			}
		}
		status = HealthLevel.UNKNOWN;
		resetTimer();
	}
	
	protected abstract HealthLevel getStatus();
	
	@Override
	protected final void execute() { // It should not be possible to override this
		status = getStatus();
		LogKitten.v(getName() + " healthCheck: " + status);
		for (HealthLevel other : commands.keySet()) {
			if (other != status) {
				for (HealthProtectCommand hpc : commands.get(other)) {
					if (hpc.isRunning()) {
						hpc.cancel();// stop all the ones for other states
					}
				}
			}
		}
		ArrayList<HealthProtectCommand> commandsToRun = commands.get(status);
		if (commandsToRun != null) {
			for (HealthProtectCommand hpc : commandsToRun) {
				if (!hpc.isRunning()) {
					hpc.start();// start all the ones for this state
				}
			}
		}
	}
	
	@Override
	protected final void interrupted() {
		LogKitten.e("ERROR: " + getName() + " health check interrupted");
	}
	
	@Override
	protected final void end() {
		for (HealthLevel level : commands.keySet()) {
			if (commands.get(level) != null) {
				for (HealthProtectCommand hpc : commands.get(level)) {
					if (hpc.isRunning()) {
						hpc.cancel();
					}
				}
			}
		}
	}
	
	protected abstract boolean finished();
	
	@Override
	protected final boolean isFinished() {
		if (!finished()) {
			return false;
		}
		for (HealthLevel level : commands.keySet()) {
			if (commands.get(level) != null) {
				for (HealthProtectCommand hpc : commands.get(level)) {
					if (hpc.isRunning()) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
