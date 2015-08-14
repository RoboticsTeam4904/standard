package org.usfirst.frc4904.cmdbased;


public interface OutPipable {
	/**
	 * This should do something with the data.
	 * It should be the most obvious and useful thing, or should be changeable
	 * 
	 * @param data
	 */
	public void writePipe(double[] data);
	
	/**
	 * This should set the mode of the pipe
	 * 
	 * @param mode
	 *        should be an Enum that the class using the OutPipe defines to set the mode
	 * @return
	 */
	public void setPipe(Enum mode);
}
