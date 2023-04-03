package org.usfirst.frc4904.standard.custom.motioncontrollers;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.opencv.core.Mat.Tuple2;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ezMotion extends CommandBase {
    public ezControl control;
    public DoubleConsumer processVariable;
    public Double initialTimestamp;
    public DoubleSupplier feedback;
    public Supplier<DoubleFunction<Tuple2<Double>>> setpointDealerDealer;
    public DoubleFunction<Tuple2<Double>> setpointDealer = null;

    public ezMotion(ezControl control, DoubleSupplier feedback, DoubleConsumer processVariable, Supplier<DoubleFunction<Tuple2<Double>>> setpointDealerDealer, Subsystem... requirements) {  // FIXME: use Pair<Double, Double>
        addRequirements(requirements);
        this.control = control;
        this.processVariable = processVariable;
        this.feedback = feedback;
        this.setpointDealerDealer = setpointDealerDealer;
    }

    public double getElapsedTime() {
        return Timer.getFPGATimestamp() - initialTimestamp;
    }

    public boolean atLatestSetpoint() {
        return this.control.atSetpoint();
    }

    @Override
    public void initialize() {
        this.setpointDealer = setpointDealerDealer.get();
        this.initialTimestamp = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        Tuple2<Double> setpoints = this.setpointDealer.apply(getElapsedTime());
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
        // FIXME Auto-generated method stub
        super.end(interrupted);
    }
}
