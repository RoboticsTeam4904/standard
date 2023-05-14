package org.usfirst.frc4904.standard.custom.motioncontrollers;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ezMotion extends CommandBase {
    public ezControl control;
    public DoubleConsumer processVariable;
    public Double initialTimestamp;
    public DoubleSupplier feedback;
    public Supplier<DoubleFunction<Pair<Double, Double>>> setpointDealerDealer;
    public DoubleFunction<Pair<Double, Double>> setpointDealer = null;

    public ezMotion(ezControl control, DoubleSupplier feedback, DoubleConsumer processVariable, Supplier<DoubleFunction<Pair<Double, Double>>> setpointDealerDealer, Subsystem... requirements) {  // FIXME: use Pair<Double, Double>
        addRequirements(requirements);
        this.control = control;
        this.processVariable = processVariable;
        this.feedback = feedback;
        this.setpointDealerDealer = setpointDealerDealer;
    }

    public ezMotion(ezControl control, DoubleSupplier feedback, DoubleConsumer processVariable, DoubleFunction<Pair<Double, Double>> setpointDealer, Subsystem... requirements) {
        this(control, feedback, processVariable, () -> setpointDealer, requirements);
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
        Pair<Double, Double> setpoints = this.setpointDealer.apply(getElapsedTime());
        double setpoint = setpoints.getFirst();
        double setpoint_dt = setpoints.getSecond();

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
