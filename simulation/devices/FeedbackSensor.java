package org.usfirst.frc4904.ourWPI.devices;

/**
 * Represents any type of sensor capable of reading data.
 * @author Finn Frankis
 * @version Jul 22,  2018
 */
public abstract class FeedbackSensor
{
    private double previousPosition;
    private double previousTime;
    private double currentPosition;
   
    /**
     * Gets the current position.
     * @return the current sensor position
     */
    public double getPosition()
    {
        return currentPosition;
    }
    
    /**
     * Gets the current velocity.
     * @return the current sensor velocity
     */
    public double getVelocity()
    {
        double currentPosition = getPosition(), currentTime = System.currentTimeMillis();
        Double deltaT = currentTime - previousTime;
        
        if (previousTime == -1 || deltaT.equals(0d))
            return 0;
        
        double velocity = (currentPosition - previousPosition) / (currentTime - previousTime);
        
        previousPosition = currentPosition; 
        previousTime = currentTime;
        
        return velocity;
    }
    
    /**
     * Sets the sensor value to a new position. This should not be used to update the 
     * position to reflect the most recent sensor reading, but instead for long-term offset or zeroing.
     * @param newPosition the new sensor position
     */
    public void setPosition (double newPosition)
    {
        previousPosition = -1;
        previousTime = -1;
        currentPosition = newPosition;
    }
    
    /**
     * Adds to the sensor value a given value. This should be used to update the position to reflect
     * the most recent sensor reading.
     * @param offset the amount to be added
     */
    protected void addToPosition (double offset)
    {
        previousPosition = currentPosition;
        previousTime = System.currentTimeMillis();
        currentPosition += offset;
    }
    
    /**
     * Sets the sensor value to a given value. This should be used to update the position to reflect the most recent
     * sensor reading. 
     * @param position the new sensor position
     */
    protected void updatePosition (double position)
    {
        previousPosition = currentPosition;
        previousTime = System.currentTimeMillis();
        currentPosition = position;
    }
}
