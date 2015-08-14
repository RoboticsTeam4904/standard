package org.usfirst.frc4904.cmdbased;


import org.usfirst.frc4904.cmdbased.custom.Named;

public abstract class HumanInterface implements Named {
	protected final String name;
	
	public HumanInterface(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}