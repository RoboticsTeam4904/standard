package org.usfirst.frc4904.cmdbased;


import org.usfirst.frc4904.cmdbased.custom.Named;

public class Driver implements Named {
	private final String name;
	
	public Driver(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}