package org.usfirst.frc4904.cmdbased;


import org.usfirst.frc4904.cmdbased.custom.Named;

public abstract class Operator extends HumanInterface implements Named {
	public Operator(String name) {
		super(name);
	}
}
