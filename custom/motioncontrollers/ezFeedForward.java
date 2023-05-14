package org.usfirst.frc4904.standard.custom.motioncontrollers;

@FunctionalInterface
public interface ezFeedForward {
    public double calculate(double setpoint, double setpoint_dt);
}
