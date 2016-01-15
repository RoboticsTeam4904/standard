package org.usfirst.frc4904.standard.commands.healthchecks;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.commands.TimedCommand;
import edu.wpi.first.wpilibj.command.Command;

public abstract class AbstractHealthcheck extends TimedCommand { // Many of our healthchecks will use timing, so a TimedCommand is needed
	protected Command dangerCommand;
	protected HealthStatus status;
	
	public AbstractHealthcheck(String name, Command dangerCommand) {
		super(name);
		status = HealthStatus.UNCERTAIN;
		this.dangerCommand = dangerCommand;
	}
	
	protected void initialize() {}
	
	private void Dangerous() {
		if (!dangerCommand.isRunning()) {
			dangerCommand.start();
		}
	}
	
	private void Caution() { // By the time we should be taking action, the situation should be considered dangerous
		LogKitten.wtf(getName() + " health status approaching dangerous"); // This is highest level because we need people to see the error
	}
	
	private void Uncertain() {
		LogKitten.wtf(getName() + " health status uncertain"); // This is higherst level because we need people to see the error
	}
	
	protected abstract HealthStatus getStatus();
	
	protected final void execute() { // It should not be possible to override this
		status = getStatus();
		switch (status) {
			case DANGEROUS:
				Dangerous();
				return;
			case CAUTION:
				Caution();
				return;
			default:
				Uncertain();
				return;
		}
	}
	
	protected void interrupted() {
		LogKitten.e("ERROR: " + getName() + " health check interrupted");
	}
	
	protected void end() {
		if (dangerCommand.isRunning()) {
			dangerCommand.cancel();
		}
	}
	
	protected abstract boolean finished();
	
	protected boolean isFinished() {
		return finished() && !dangerCommand.isRunning();
	}
}
