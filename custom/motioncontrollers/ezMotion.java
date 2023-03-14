package org.usfirst.frc4904.standard.custom.motioncontrollers;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

import org.opencv.core.Mat.Tuple2;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ezMotion extends CommandBase {
    public ezControl control;
    public DoubleConsumer processVariable;
    public Double initialTimestamp;
    public DoubleSupplier feedback;
    public DoubleFunction<Tuple2<Double>> setpointProvider;

    public ezMotion(ezControl control, DoubleSupplier feedback, DoubleConsumer processVariable, DoubleFunction<Tuple2<Double>> setpoint, Subsystem... requirements) {
        addRequirements(requirements);
        this.control = control;
        this.processVariable = processVariable;
        this.feedback = feedback;
        this.setpointProvider = setpoint;
    }

    public double getElapsedTime() {
        return Timer.getFPGATimestamp() - initialTimestamp;
    }

    public boolean atLatestSetpoint() {
        return this.control.atSetpoint();
    }

    @Override
    public void initialize() {
        this.initialTimestamp = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        Tuple2<Double> setpoints = this.setpointProvider.apply(getElapsedTime());
        double setpoint = setpoints.get_0();
        double setpoint_dt = setpoints.get_1();

        control.updateSetpoint(setpoint, setpoint_dt);
        double controlEffort = control.calculate(feedback.getAsDouble(), getElapsedTime());
        processVariable.accept(controlEffort);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }
}
