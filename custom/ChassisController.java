package org.usfirst.frc4904.standard.custom;


/**
 * A generic interface for a ChassisController, something
 * that can control the movement of a chassis through the
 * chassis move command.
 */
public interface ChassisController {
	/**
	 * @return
	 * 		X value that the Controller wants
	 */
	public double getX();
	
	/**
	 * @return
	 * 		Y value that the Controller wants
	 */
	public double getY();
	
	/**
	 * @return
	 * 		Turn speed that the Controller wants
	 */
	public double getTurnSpeed();
}
