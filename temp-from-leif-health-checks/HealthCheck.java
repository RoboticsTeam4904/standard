package org.usfirst.frc4904.standard.commands.safety;


import java.util.LinkedList;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author avery cow
 * @todo remove this comment because there is nothing in it
 */
public abstract class HealthCheck extends Command {
	private volatile HealthLevel currentStatus = HealthLevel.UNKNOWN;
	protected final LinkedList<HealthCheckAlarm> alarms = new LinkedList<>();
	private final String healthCheckName;
	
	protected HealthCheck(String healthCheckName) {
		this.healthCheckName = healthCheckName;
	}
	
	protected abstract HealthLevel calculateHealthStatus();
	
	@Override
	protected final void execute() {
		setHealthStatus(calculateHealthStatus());
	}
	
	/**
	 * The subclass should call this when the health check is finished
	 * Protected so that only the subclass can call it
	 * 
	 * @param updatedLevel
	 */
	private void setHealthStatus(final HealthLevel updatedLevel) {
		if (updatedLevel == null) {
			throw new IllegalArgumentException("can't be null");
		}
		if (currentStatus == updatedLevel) {// nothing new, don't update the alarms
			return;
		}
		this.currentStatus = updatedLevel;
		for (HealthCheckAlarm alarm : alarms) {
			alarm.updateStatus(updatedLevel);
		}
	}
	
	/**
	 * If anything needs the current health level, call this
	 * 
	 * @return
	 */
	public HealthLevel getHealthStatus() {
		return currentStatus;
	}
	
	@Override
	public String getName() {
		return healthCheckName;
	}
}
