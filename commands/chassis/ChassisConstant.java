package org.usfirst.frc4904.standard.commands.chassis;


import java.util.Set;

import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ChassisConstant implements ChassisController, Command {
	protected final ChassisMove move;
	protected final double x;
	protected final double y;
	protected final double turn;

	@Override
	public Set<Subsystem> getRequirements() {
		// TODO Auto-generated method stub
		// we need nothing

		return null;
	}
	public ChassisConstant(Chassis chassis, double x, double y, double turn, double timeout) {
		move = new ChassisMove(chassis, this);
		this.x = x;
		this.y = y;
		this.turn = turn;
		setTimeout(timeout);
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
		move.start();
	}

	@Override
	public void execute() {}

	@Override
	public boolean isFinished() {
		return move.isFinished() || isTimedOut();
	}

	@Override
	public void end(boolean interrupted) {
		move.cancel();
	}

	@Override
	public void interrupted() {
		move.cancel();
	}
}
