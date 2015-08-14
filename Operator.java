package org.usfirst.frc4904.cmdbased;


import org.usfirst.frc4904.cmdbased.custom.Named;

public class Operator implements Named {
	private final String name;
	
	public Operator(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
