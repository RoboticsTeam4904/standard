package org.usfirst.frc4904.cmdbased.custom;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class CommandSendableChooser extends SendableChooser {
	public void addObject(Command object) {
		super.addObject(object.getName(), object);
	}
	
	public void addDefault(Command object) {
		super.addDefault(object.getName() + " (default)", object);
	}
	
	public Command getSelected() {
		return (Command) super.getSelected();
	}
}
