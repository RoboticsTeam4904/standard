package org.usfirst.frc4904.standard.humaninterface;


import org.usfirst.frc4904.standard.custom.Named;

public abstract class Driver extends HumanInterface implements Named {
	public Driver(String name) {
		super(name);
	}
	
	public abstract double getX();
	
	public abstract double getY();
	
	public abstract double getTurnSpeed();
}