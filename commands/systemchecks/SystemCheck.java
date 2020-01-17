package org.usfirst.frc4904.standard.commands.systemchecks;


import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import edu.wpi.first.wpilibj.SendableBase;

/**
 * A BaseCheck that takes in SendableBases as systems
 */
public abstract class SystemCheck extends BaseCheck {
	protected HashMap<String, StatusMessage> statuses;

	/**
	 * Constructs a basic check with SendableBases:
	 * 
	 * @param name
	 *                name of command
	 * @param timeout
	 *                duration of the command
	 * @param systems
	 *                SendableBase systems
	 */
	public SystemCheck(String name, double timeout, SendableBase... systems) {
		super(name, timeout, Arrays.asList(systems).stream().map(system -> system.getName()).collect(Collectors.toList())
			.toArray(String[]::new));
	}

	/**
	 * Constructs a basic check with SendableBases:
	 * 
	 * @param timeout
	 *                duration of the command
	 * @param systems
	 *                SendableBase systems
	 */
	public SystemCheck(double timeout, SendableBase... systems) {
		this("SystemCheck", timeout, systems);
	}

	/**
	 * Constructs a basic check with SendableBases:
	 * 
	 * @param name
	 *                name of command
	 * @param systems
	 *                SendableBase systems
	 */
	public SystemCheck(String name, SendableBase... systems) {
		this(name, DEFAULT_TIMEOUT, systems);
	}

	/**
	 * Constructs a basic check with SendableBases:
	 * 
	 * @param systems
	 *                SendableBase systems
	 */
	public SystemCheck(SendableBase... systems) {
		this("SystemCheck", systems);
	}
}