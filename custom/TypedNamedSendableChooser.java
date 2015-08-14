package org.usfirst.frc4904.cmdbased.custom;


import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class TypedNamedSendableChooser<T extends Named> extends SendableChooser {
	public void addObject(T object) {
		super.addObject(object.getName(), object);
	}
	
	public void addDefault(T object) {
		super.addDefault(object.getName() + " (default)", object);
	}
	
	@SuppressWarnings("unchecked")
	public T getSelected() {
		return (T) super.getSelected();
	}
}
