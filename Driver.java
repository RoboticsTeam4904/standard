package org.usfirst.frc4904.cmdbased;


import org.usfirst.frc4904.cmdbased.custom.Named;

public abstract class Driver extends HumanInterface implements Named {
	public Driver(String name) {
		super(name);
	}
}