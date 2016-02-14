package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisSet extends Command implements ChassisController {
	private ChassisMove move;
	private double x;
	private double y;
	private double turn;
	
	public ChassisSet(Chassis chassis, double x, double y, double turn, double timeout) {
		move = new ChassisMove(chassis, this);
		this.x = x;
		this.y = y;
		this.turn = turn;
		setTimeout(timeout);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getTurnSpeed() {
		return turn;
	}
	
	protected void initialize() {
		move.start();
	}
	
	protected void execute() {}
	
	protected boolean isFinished() {
		return move.isFinished() || isTimedOut();
	}
	
	protected void end() {
		move.cancel();
	}
	
	protected void interrupted() {
		move.cancel();
	}
}
