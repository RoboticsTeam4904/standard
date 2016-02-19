package org.usfirst.frc4904.standard.humaninput;


import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.Named;

/**
 * Driver specific version of HumanInterface.
 * Also designed to be passed around to control
 * the chassis.
 *
 */
public abstract class Driver extends HumanInput implements Named, ChassisController {
	public Driver(String name) {
		super(name);
	}
	
	/**
	 *
	 * @return
	 * 		X value that the Driver wants
	 */
	@Override
	public abstract double getX();
	
	/**
	 *
	 * @return
	 * 		Y value that the Driver wants
	 */
	@Override
	public abstract double getY();
	
	/**
	 *
	 * @return
	 * 		Turn speed that the Driver wants
	 */
	@Override
	public abstract double getTurnSpeed();
}