package org.usfirst.frc4904.ourWPI.devices;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a PID controller.
 * @author Finn Frankis
 * @version Jul 15, 2018
 */
public class PIDController
{
    private Map<Integer, Double> kF;
    private Map<Integer, Double> kP;
    private Map<Integer, Double> kI;
    private Map<Integer, Double> kD;
    private Map<Integer, Double> iZone;
    
    private int primarySlot;
    private int auxiliarySlot;
    
    private double lastError;
    private double lastTime;
    private double errorSum;
    private boolean hasRun;
    
    private double minOutput;
    private double maxOutput;
    
    /**
     * Constructs a new PIDController.
     * @param minOutput the minimum possible output for the controller to return
     * @param maxOutput the maximum possible output for the controller to return
     */
    public PIDController (double minOutput, double maxOutput)
    {
        kF = new HashMap<Integer, Double>();
        kP = new HashMap<Integer, Double>();
        kI = new HashMap<Integer, Double>();
        kD = new HashMap<Integer, Double>();
        iZone = new HashMap<Integer, Double>();
        setValuesToZero (kF, kP, kI, kD, iZone);
        
        errorSum = 0;
        lastError = 0;
        hasRun = false;
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
    }
    
    public void setValuesToZero (Map<Integer, Double>... maps)
    {
        for (Map<Integer, Double> map : maps)
        {
            for (int i = 0; i < 4; i++)
                map.put(i, 0d);
        }
    }
    
    /**
     * Configures the kF value at a given PID slot.
     * @param kF the value to be stored
     * @param slot the PID slot at which the constants will be stored [0,3]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     */
    public void config_kF(double kF, int slot, int timeout)
    {
        this.kF.put(slot, kF);
    }
    
    /**
     * Configures the kP value at a given PID slot.
     * @param kP the value to be stored
     * @param slot the PID slot at which the constants will be stored [0,3]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     */
    public void config_kP(double kP, int slot, int timeout)
    {
        this.kP.put(slot, kP);
    }
    
    /**
     * Configures the kI value at a given PID slot.
     * @param kI the value to be stored
     * @param slot the PID slot at which the constants will be stored [0,3]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     */
    public void config_kI(double kI, int slot, int timeout)
    {
        this.kI.put(slot, kI);
    }
    
    /**
     * Configures the kD value at a given PID slot.
     * @param kD the value to be stored
     * @param slot the PID slot at which the constants will be stored [0,3]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     */
    public void config_kD(double kD, int slot, int timeout)
    {
        this.kD.put(slot, kD);
    }
    
    /**
     * Configures the I-Zone value at a given PID slot.
     * @param iZone the value to be stored
     * @param slot the PID slot at which the constants will be stored [0,3]
     * @param timeout the time after which the attempts to send the command cease (if it continually fails
     * to send)
     */
    public void config_IntegralZone(double iZone, int slot, int timeout)
    {
        this.iZone.put(slot, iZone);
    }
    
    /**
     * Selects the PID constants to use in either the primary or auxiliary PID loop.
     * @param slotIndex the PID slot index [0,3]
     * @param loopIndex the PID loop index (primary/auxiliary) [0,1]
     */
    public void selectProfileSlot(int slotIndex, int loopIndex)
    {
        if (loopIndex == 0)
            primarySlot = slotIndex;
        else
            auxiliarySlot = slotIndex;
    }
    
    /**
     * Gets the necessary motor output given the actual position and the setpoint.
     * @param actual the current position (likely as input from a sensor such as a gyro
     * or encoder)
     * @param setpoint the desired final position
     * @return the output required for the motors
     */
    public double getOutput (double actual, double setpoint)
    {
        double currentTime = System.currentTimeMillis();
        double error = setpoint - actual;
        
        double output_F = kF.get(primarySlot) * setpoint;
        
        double output_P = kP.get(primarySlot) * error;
        
        double output_I = 0;
        double output_D = 0; 
        if (hasRun) {
            if (Math.abs(error) >= iZone.get(primarySlot))
                errorSum = 0;
            output_I = kI.get(primarySlot) * errorSum;
            errorSum += error * (currentTime - lastTime);
            
            output_D = -kD.get(primarySlot) * (error - lastError) / (currentTime - lastTime);
        }

        lastError = error;
        lastTime = currentTime;
        hasRun = true;

        double ret = output_F + output_P + output_I + output_D;

        if(ret > maxOutput) {
            ret = maxOutput;
        }
        if(ret < minOutput) {
            ret = minOutput;
        }

        return ret;
    }
    
    /**
     * Gets the closed loop error at a given loop index.
     * @param loopIndex the PID loop index to check (primary/auxiliary) [0,1]
     * @param timeout the time after which the program ceases to reattempt the command if it fails
     * @return closed loop error
     */
    public double getClosedLoopError (double loopIndex, double timeout)
    {
        return lastError;
    }
}
