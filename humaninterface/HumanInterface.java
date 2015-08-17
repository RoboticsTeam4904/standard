package org.usfirst.frc4904.cmdbased.humaninterface;


import org.usfirst.frc4904.cmdbased.custom.Named;

public abstract class HumanInterface implements Named {
	protected final String name;
	
	public HumanInterface(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * A function where the driver's and operator's controls are bound to commands
	 * Can't be done in the constructor because constructors are called too early
	 */
	public abstract void bindCommands();
}