package org.usfirst.frc4904.ourWPI.devices;

/**
 * Represents a magnetic encoder.
 * @author Finn Frankis
 * @version Jul 20, 2018
 */
public class SimpleEncoder extends FeedbackSensor {
    
    private TalonSRX controller; 
    
    /**
     * Constructs a new Encoder.
     * @param orangePort the pin into which the orange encoder wire is plugged in
     * @param brownPort the pin into which the brown encoder wire is plugged in
     */
    public SimpleEncoder (int orangePort, int brownPort) {

    }
    
    protected FeedbackSensor setController (TalonSRX controller)
    {
        this.controller = controller;
        return this;
    }
}
