package org.usfirst.frc4904.standard.commands.systemchecks;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Arrays;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.wpilibj.command.Command;

public abstract class BaseCheck extends Command implements Check {
	protected static final double DEFAULT_TIMEOUT = 5;
	protected HashMap<String, StatusMessage> statuses;
	protected final String[] systemNames;

	public BaseCheck(String checkName, double timeout, String... systemNames) {
		super(checkName, timeout);
		this.systemNames = systemNames;
		initStatuses();
	}

	public BaseCheck(double timeout, String... systemNames) {
		this("Check", timeout, systemNames);
	}

	public BaseCheck(String... systemNames) {
		this(DEFAULT_TIMEOUT, systemNames);
	}

	@Override
	public boolean isFinished() {
		return isTimedOut();
	}

	@Override
	public void end() {
		outputStatuses();
	}

	public void setStatus(String name, SystemStatus status, Exception... exceptions) {
		statuses.put(name, new StatusMessage(status, exceptions));
	}

	public void initStatus(String name) {
		setStatus(name, SystemStatus.PASS);
	}

	public void initStatuses() {
		for (String name : systemNames) {
			initStatus(name);
		}
	}

	public void updateStatus(String key, StatusMessage.SystemStatus status, Exception... exceptions) {
		setStatus(key, status, Stream.concat(Arrays.stream(getStatusMessage(key).exceptions), Arrays.stream(exceptions))
			.toArray(Exception[]::new));
	}

	public StatusMessage getStatusMessage(String key) {
		return statuses.get(key);
	}

	public void outputStatuses() {
		LogKitten.wtf(getName() + " Statuses:");
		for (Map.Entry<String, StatusMessage> entry : statuses.entrySet()) {
			String name = entry.getKey();
			StatusMessage message = entry.getValue();
			if (message.status == SystemStatus.PASS) {
				LogKitten.d("Subsystem: " + name + ", Status: PASS");
			} else {
				LogKitten.wtf("Subsystem: " + name + ", Status: FAIL");
				for (Exception e : message.exceptions) {
					LogKitten.wtf(e);
				}
			}
		}
	}
}