package org.usfirst.frc4904.standard;

public class RobotModeKitten extends Kitten {
	protected String mode;
	
	public RobotModeKitten(String category, String mode) {
		super(category);
		this.mode = mode;
	}
	
	public String getMode() {
		return this.mode;
	}
}
