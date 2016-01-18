package org.usfirst.frc4904.standard.commands.safety;


public abstract class HealthCheckAlarm {
	protected final HealthCheck healthCheck;
	protected volatile AlarmState alarmState;
	
	protected HealthCheckAlarm(HealthCheck healthCheck) {
		this.healthCheck = healthCheck;
		this.alarmState = AlarmState.INSUFFICIENT;
	}
	
	public final HealthLevel getCurrentSubjectStatus() {
		return healthCheck.getHealthStatus();
	}
	
	public final AlarmState getAlarmState() {
		return alarmState;
	}
	
	/**
	 * Get the health check that is the subject of this alarm
	 * 
	 * @return
	 */
	public final HealthCheck getSubject() {
		return healthCheck;
	}
	
	/**
	 * This could get the current subject status, but actually that's not a good idea. Since this function is called asynchronously in another thread as the health check, the state could have changed again in the meantime. This makes sure that every new state will call these alarms.
	 * This function is also in charge of updateing alarmState acccordingly
	 * 
	 * @param updatedLevel
	 *        the current level
	 */
	abstract void updateStatus(HealthLevel updatedLevel);// this is neither public nor private because I want it to be available to classes in this same package, but no one eles
}
