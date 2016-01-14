package org.usfirst.frc4904.standard.commands.healthchecks;


import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

public abstract class AbstractHealthcheck extends Command {
	protected Command dangerCommand;
	protected Command cautionCommand;
	protected Command safeCommand; // I am not sure if we will need this
	protected Command uncertainCommand; // I am not sure if we will need this,
										// it may be better to just log on the ERROR or WARNING channel
	protected HealthStatus status;
	
	public AbstractHealthcheck(String name) {
		super(name);
		status = HealthStatus.UNCERTAIN;
	}
	
	protected void initialize() {}
	
	protected void Dangerous() {
		if (!dangerCommand.isRunning()) {
			dangerCommand.start();
		}
	}
	
	protected void Caution() {
		if (!cautionCommand.isRunning()) {
			cautionCommand.start();
		}
	}
	
	protected void Safe() {
		if (!safeCommand.isRunning()) {
			safeCommand.start();
		}
	}
	
	protected void Uncertain() {
		if (!uncertainCommand.isRunning()) {
			uncertainCommand.start();
		}
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
			case SAFE:
				Safe();
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
		if (cautionCommand.isRunning()) {
			cautionCommand.cancel();
		}
		if (safeCommand.isRunning()) {
			safeCommand.cancel();
		}
		if (uncertainCommand.isRunning()) {
			uncertainCommand.cancel();
		}
	}
	
	protected abstract boolean finished();
	
	protected boolean isFinished() {
		return finished() && !dangerCommand.isRunning() && !cautionCommand.isRunning();
	}
}
