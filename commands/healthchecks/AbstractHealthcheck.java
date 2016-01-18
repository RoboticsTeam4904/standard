package org.usfirst.frc4904.standard.commands.healthchecks;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.commands.TimedCommand;

public abstract class AbstractHealthCheck extends TimedCommand { // Many of our healthchecks will use timing, so a TimedCommand is needed
	protected final HealthProtectCommand dangerCommand;
	protected volatile HealthLevel status;
	
	public AbstractHealthCheck(String name) {
		this(name, null);
	}
	
	public AbstractHealthCheck(String name, HealthProtectCommand dangerCommand) {
		super(name);
		status = HealthLevel.UNKNOWN;
		this.dangerCommand = dangerCommand;
		setRunWhenDisabled(true);
	}
	
	public void reset() {
		if (dangerCommand.isRunning()) {
			dangerCommand.cancel();
		}
		dangerCommand.reset();
		status = HealthLevel.UNKNOWN;
		resetTimer();
	}
	
	/**
	 * These commands are handled automatically, and should not be messed with
	 */
	private void dangerous() {
		if (!dangerCommand.isRunning()) {
			dangerCommand.start();
		}
		LogKitten.wtf(getName() + " health status dangerous"); // This is highest level because we need people to see the error
	}
	
	private void unsafe() { // By the time we should be taking action, the situation should be considered dangerous
		LogKitten.f(getName() + " health status approaching dangerous"); // This is highest level because we need people to see the error
	}
	
	private void safe() {}
	
	private void perfect() {}
	
	private void unknown() {
		LogKitten.f(getName() + " health status uncertain"); // This is highest level because we need people to see the error
	}
	
	protected abstract HealthLevel getStatus();
	
	@Override
	protected final void execute() { // It should not be possible to override this
		status = getStatus();
		LogKitten.v(getName() + " healthCheck: " + status);
		switch (status) {
			case DANGEROUS:
				dangerous();
				return;
			case SAFE:
				safe();
				return;
			case UNSAFE:
				unsafe();
				return;
			case PERFECT:
				perfect();
				return;
			case UNKNOWN:
				unknown();
				return;
			default:
				unknown();
				return;
		}
	}
	
	@Override
	protected final void interrupted() {
		LogKitten.e("ERROR: " + getName() + " health check interrupted");
	}
	
	@Override
	protected final void end() {
		if (dangerCommand.isRunning()) {
			dangerCommand.cancel();
		}
	}
	
	protected abstract boolean finished();
	
	@Override
	protected final boolean isFinished() {
		return finished() && !dangerCommand.isRunning();
	}
}
