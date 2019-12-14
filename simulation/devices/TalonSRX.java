package org.usfirst.frc4904.ourWPI.devices;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc4904.ourWPI.util.Constants;


/**
 * Represents a motor controller to simulate the TalonSRX.
 * @author Finn Frankis
 * @version Jul 8, 2018
 */
public class TalonSRX extends PIDController
{
    private FeedbackSensor[] selectedSensors;
    private Map<FeedbackDevice, FeedbackSensor> sensors; 
    private double prevOutput;

    
    /**
     * The mode which will be run on the Talon when the set() method is applied.
     * @author Finn Frankis
     * @version Jul 8, 2018
     */
    public enum ControlMode
    {
        /**
         * Specifies that the Talon should drive based on a percent output [0,1].
         */
        PercentOutput, 
        /**
         * Specifies that the robot should closed loop to a given position 
         * as read by the primary sensor.
         */
        Position, 
        /**
         * Specifies that the robot should closed loop to a given velocity
         * as read by the primary sensor.
         */
        Velocity, 
        
        /**
         * Specifies that the Talon should be disabled.
         */
        Disabled;
    }
    
    /**
     * Represents the possible demand types used in the .set() method.
     * @author Finn Frankis
     * @version Jul 30, 2018
     */
    public enum DemandType
    {
        /**
         * Specifies an arbitrary feed forward to always be added to the motor output.
         */
        FeedForward;
    }
    
    /**
     * Represents the possible sensor types.
     * @author Finn Frankis
     * @version Jul 30, 2018
     */
    public enum FeedbackDevice
    {
        /**
         * Refers to a magnetic encoder as a sensor type.
         */
        MagneticEncoder;
    }
    
    /**
     * Constructs a new Talon using a digital H-Bridge motor.
     * 
     * @param enablePort the GPIO port used to enable the channel (the magnitude of the speed)
     * @param forwardPort the GPIO port which is used to change the sign of the forward direction
     * @param backwardPort the GPIO port which is used to change the sign of the backward direction
     */
    public TalonSRX(int enablePort, int forwardPort, int backwardPort)
    {
        super (-1, 1);
        // motor = new DigitalMotor(forwardPort, backwardPort, enablePort);
        initializeVariables();
    }
    
    /**
     * Constructs a new Talon using a Servo port.
     * 
     * @param port the GPIO port into which the Servo signal line is connected
     */
    public TalonSRX(int port)
    {
        super (-1, 1);
        // motor = new Servo(port);
        initializeVariables();
    }
    
    /**
     * Initializes variables which are independent of motor type.
     */
    private void initializeVariables()
    {
        selectedSensors = new FeedbackSensor[2];
        sensors = new HashMap<FeedbackDevice, FeedbackSensor>();
    }
    /**
     * Sets the Talon to a given output.
     * @param mode the type of control to be performed
     * on the talon (including percent output, velocity, and position)
     * @param magnitude the magnitude of the value to be set
     */
    public void set(ControlMode mode, double magnitude) {
        double output = 0;
        
        if (mode == ControlMode.PercentOutput) {
            output = magnitude;
        }
        else if (mode == ControlMode.Position) {
            
        }
        else if (mode == ControlMode.Velocity) {
            
        }
        prevOutput = output;
    }
    
    /**
     * Sets the Talon to a given output.
     * @param mode the mode of control to be performed on the Talon (including % output, velocity, and position)
     * @param magnitude the magnitude of the value to be set
     * @param dt the demand type to be added to the output (like a feed forward or an auxiliary sensor value)
     * @param demandValue the magnitude of the demand type
     */
    public void set(ControlMode mode, double magnitude, DemandType dt, double demandValue) {
       // TODO: Simulate
    }
    
    /**
     * Sets up the Encoder by specifying its GPIO location
     * @param orangePin the orange pin for the encoder
     * @param brownPin the brown pin for the encoder
     */
    public void setupEncoder (int orangePin, int brownPin)
    {
        sensors.put(FeedbackDevice.MagneticEncoder, new SimpleEncoder(orangePin, brownPin)
                .setController(this));
    }
    
    /**
     * Configures the feedback sensor to be selected for reading at a given PID loop, 
     * provided it has been set up.
     * @param fd the type of sensor to be selected (like MagneticEncoder)
     * @param loopIndex the PID loop index (primary/auxiliary) [0,1]
     * @param timeout the time after which the CAN command stops being attempted to send
     */
    public void configSelectedFeedbackSensor(FeedbackDevice fd, int loopIndex, int timeout)
    {
        if (!sensors.containsKey(fd))
            throw new InvalidParameterException ("Sensor has not been set up.");
        selectedSensors[loopIndex] = sensors.get(fd);
    }
    
    /**
     * Gets the sensor position at the given loop index.
     * @param loopIndex the PID loop index (primary/auxiliary) [0, 1]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     * @return the current sensor position
     */
    public double getSelectedSensorPosition (int loopIndex, int timeout) {
        if (selectedSensors[loopIndex] == null) {
                throw new InvalidParameterException("A selected sensor has not been configured. Use configSelectedFeedbackSensor.");
        }
        return selectedSensors[loopIndex].getPosition();
    }
    
    /**
     * Gets the sensor velocity at the given loop index.
     * @param loopIndex the PID loop index (primary/auxiliary) [0,1]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     * @return the sensor velocity
     */
    public double getSelectedSensorVelocity (int loopIndex, int timeout) {
        if (selectedSensors[loopIndex] == null) {
                throw new InvalidParameterException("A selected sensor has not been configured. Use configSelectedFeedbackSensor.");
        }
        return selectedSensors[loopIndex].getVelocity();
    }
    
    /**
     * Sets the sensor position to a given value.
     * @param sensorValue the value to which the sensor should be set
     * @param loopIndex the PID loop index of the sensor to be set [0,1]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     */
    public void setSelectedSensorPosition (double sensorValue, int loopIndex, int timeout) {
        selectedSensors[loopIndex].setPosition(sensorValue);
    }
    
    protected double getOutputDirection() {
        return Math.signum(prevOutput);
    }
}
