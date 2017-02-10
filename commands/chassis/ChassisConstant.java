package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisConstant extends Command implements ChassisController {
	protected final ChassisMove move;
	protected final double x;
	protected final double y;
	protected final double turn;

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
	protected void initialize() {
		move.start();
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return move.isFinished() || isTimedOut();
	}

	@Override
	protected void end() {
		move.cancel();
	}

	@Override
	protected void interrupted() {
		move.cancel();
	}
}
