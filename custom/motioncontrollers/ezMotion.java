package org.usfirst.frc4904.standard.custom.motioncontrollers;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.usfirst.frc4904.standard.custom.motioncontrollers.ezMotion.SetpointSupplier.EndSignal;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ezMotion extends Command {
    public ezControl control;
    public DoubleConsumer processVariable;
    public Double initialTimestamp;
    public DoubleSupplier feedback;

    private double setpoint;
    private double setpoint_dt;

    public Supplier<SetpointSupplier<Pair<Double, Double>>> setpointDealerDealer;
    public SetpointSupplier<Pair<Double, Double>> setpointDealer = null;

    public Command onArrival;
    
    public ezMotion(ezControl control, 
                    DoubleSupplier feedback, 
                    DoubleConsumer processVariable, 
                    Supplier<SetpointSupplier<Pair<Double, Double>>> setpointDealerDealer, 
                    Command onArrival, 
                    Subsystem... requirements) {

        addRequirements(requirements);
        this.control = control;
        this.processVariable = processVariable;
        this.feedback = feedback;
        this.setpointDealerDealer = setpointDealerDealer;
        this.onArrival = onArrival != null? (onArrival) : (new InstantCommand(() -> {}));
    }

    public ezMotion(ezControl control, 
                    DoubleSupplier feedback, DoubleConsumer processVariable, 
                    Supplier<SetpointSupplier<Pair<Double, Double>>> setpointDealerDealer, 
                    Subsystem... requirements) 
    { this(control, feedback, processVariable, setpointDealerDealer, new InstantCommand(() -> {}), requirements); }

    public ezMotion(ezControl control, 
                    DoubleSupplier feedback, 
                    DoubleConsumer processVariable, 
                    SetpointSupplier<Pair<Double, Double>> setpointDealer, 
                    Command onArrival, 
                    Subsystem... requirements) 
    { this(control, feedback, processVariable, () -> setpointDealer, onArrival, requirements); }

    public ezMotion(ezControl control, 
                    DoubleSupplier feedback, 
                    DoubleConsumer processVariable, 
                    SetpointSupplier<Pair<Double, Double>> setpointDealer, 
                    Subsystem... requirements) 
    { this(control, feedback, processVariable, () -> setpointDealer, requirements); }

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
        Pair<Double, Double> setpoints;
        try {
            setpoints = this.setpointDealer.apply(getElapsedTime());
            setpoint = setpoints.getFirst();
            setpoint_dt = setpoints.getSecond();

        } catch (EndSignal e) {
            onArrival.schedule();
        }

        control.updateSetpoint(setpoint, setpoint_dt);
        double controlEffort = control.calculate(feedback.getAsDouble(), getElapsedTime());
        processVariable.accept(controlEffort);
    }

    @Override
    public boolean isFinished() { return false; }

    @FunctionalInterface
    public interface SetpointSupplier<R> {
        public class EndSignal extends Throwable {}
        
        public R apply(double num) throws EndSignal;
    }
}
