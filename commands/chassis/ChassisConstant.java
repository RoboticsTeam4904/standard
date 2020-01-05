package org.usfirst.frc4904.standard.commands.chassis;

import java.util.*;

import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;

public class ChassisConstant implements Command, ChassisController {
	protected final CommandGroupBase move;
	protected final double x;
	protected final double y;
	protected final double turn;
	protected final double timeout;
	private Chassis chassis;

	public ChassisConstant(Chassis chassis, double x, double y, double turn, double timeout) {
		move = new ChassisMove(chassis, this).withTimeout(timeout);
		this.timeout = timeout;
		this.x = x;
		this.y = y;
		this.turn = turn;
		this.chassis = chassis;
	}

	@Override
	public Set<Subsystem> getRequirements() {
		HashSet<Subsystem> set = new HashSet<Subsystem>();
		set.add(this.chassis);
		return set;
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
		move.initialize();
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		// the command has timed out if the time since scheduled is greater than the
		// timeout
		return move.isFinished() || CommandScheduler.getInstance().timeSinceScheduled(this) > this.timeout;
	}

	@Override
	public void end(boolean interrupted) {
		move.cancel();
	}
}
