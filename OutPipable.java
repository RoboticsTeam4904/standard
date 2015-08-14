package org.usfirst.frc4904.cmdbased;


public interface OutPipable {
	/**
	 * This should do something with the data.
	 * It should be the most obvious and useful thing, or should be changable
	 * 
	 * @param data
	 */
	public void writePipe(double[] data);
	
	/**
	 * This should set the mode of the pipe
	 * 
	 * @param mode
	 */
	public void setPipe(int mode);
}
