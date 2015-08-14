package org.usfirst.frc4904.cmdbased.custom.controllers;


import org.usfirst.frc4904.cmdbased.InPipable;
import edu.wpi.first.wpilibj.buttons.Button;

public interface Controller extends InPipable {
	/**
	 * Controller pipe modes:
	 * 0: leftStick X, leftStick Y, rightStick X
	 * 1: rightStick X, rightStick Y
	 * 2: leftStick X, leftStick Y, rightStick X, rightStick Y
	 */
	public void setPipe(int mode);
	
	/**
	 * 
	 * @return an array of buttons from this controller
	 */
	public Button[] getButtons();
}
