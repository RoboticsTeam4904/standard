package org.usfirst.frc4904.standard.custom.motioncontrollers;

import java.util.function.BiFunction;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class ezControl implements BiFunction<Double, Double, Double> {
    private final ezControlMethod controller;
    private double setpoint;
    private double setpoint_dt;

    public ezControl(double kP, double kI, double kD, ezFeedForward F) {
        this.controller = new ezControlMethod(new PIDController(kP, kI, kD), F);
    }


    public boolean atSetpoint() {
        return this.controller.pid.atSetpoint();
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
        this.setpoint_dt = 0;
        this.controller.pid.setSetpoint(setpoint);
    }

    public void updateSetpoint(double setpoint, double setpoint_dt) {
        if (setpoint != this.setpoint) {
            // slightly expensive call that clears integral
            this.controller.pid.setSetpoint(this.setpoint);
        }
        this.setpoint = setpoint;
        this.setpoint_dt = setpoint_dt;
    }

    public void setIntegratorRange(double kIMin, double kIMax) {
        this.controller.pid.setIntegratorRange(kIMin, kIMin);
    }

    public double calculate(double measurement, double elapsed) {
        // FIXME, revert logging
        // SmartDashboard.putNumber("setpoint", this.setpoint);
        // SmartDashboard.putNumber("setpoint_dt", this.setpoint_dt);

        double pidout = this.controller.pid.calculate(measurement);
        // System.out.println(pidout);
        // SmartDashboard.putNumber("Feedback", measurement);
        // SmartDashboard.putNumber("PID out", pidout);
        return pidout + this.controller.F.calculate(this.setpoint, this.setpoint_dt);
    }

    // very similar to TrapezoidProfile.State
    @Override
    public Double apply(Double measurement, Double elapsed_period) {
        return calculate(measurement, elapsed_period);
    }

    // VERY TEMPTED
    public class ezControlState extends TrapezoidProfile.State {}

    // OK maybe a bit bad but technically good practice and I'm putting it in here anyways
    public class ezControlMethod {
        public final PIDController pid;
        public final ezFeedForward F;

        public ezControlMethod(PIDController pid, ezFeedForward F) {
            this.pid = pid;
            this.F = F;
        }
    }

    public ezControlMethod getControl() {
        return this.controller;
    }

}
