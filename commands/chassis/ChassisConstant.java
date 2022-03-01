package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class ChassisConstant extends CommandBase implements ChassisController {
    protected final ParallelRaceGroup move;
    protected final double x;
    protected final double y;
    protected double turn;
    protected double timeout;

    /**
     * 
     * @param name
     * @param chassis
     * @param x
     * @param y
     * @param turn
     * @param timeout
     */
    public ChassisConstant(String name, Chassis chassis, double x, double y, double turn, double timeout) {
        move = new ChassisMove(chassis, this).withTimeout(timeout);
        this.timeout = timeout;
        this.x = x;
        this.y = y;
        this.turn = turn;
        setName(name);
        addRequirements(chassis);
    }

    /**
     * 
     * @param chassis
     * @param x
     * @param y
     * @param turn
     * @param timeout
     */
    public ChassisConstant(Chassis chassis, double x, double y, double turn, double timeout) {
        this("Chassis Constant", chassis, x, y, turn, timeout);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getTurnSpeed() {
        return turn;
    }

    @Override
    public void initialize() {
        move.schedule();
    }

    /**
     *
     * The command has timed out if the time since scheduled is greater than the
     * timeout
     * 
     * @return finished
     * 
     */
    @Override
    public boolean isFinished() {
        return move.isFinished() || CommandScheduler.getInstance().timeSinceScheduled(this) >= this.timeout;
    }

    @Override
    public void end(boolean interrupted) {
        move.cancel();
    }
}
