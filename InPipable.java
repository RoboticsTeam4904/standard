package org.usfirst.frc4904.cmdbased;


public interface InPipable {
	/**
	 * This should return some data
	 * It should be the most useful data, or should be set via setPipe
	 * 
	 * @return
	 */
	public double[] readPipe();
	
	/**
	 * This should set the mode of the readPipe
	 * 
	 * @param mode
	 */
	public void setPipe(int mode);
}
