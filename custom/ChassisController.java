package org.usfirst.frc4904.standard.custom;


public interface ChassisController {
	/**
	 * 
	 * @return
	 * 		X value that the Controller wants
	 */
	public double getX();
	
	/**
	 * 
	 * @return
	 * 		Y value that the Controller wants
	 */
	public double getY();
	
	/**
	 * 
	 * @return
	 * 		Turn speed that the Controller wants
	 */
	public double getTurnSpeed();
}
