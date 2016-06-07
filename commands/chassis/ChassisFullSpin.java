package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.InjectedCommand;
import org.usfirst.frc4904.standard.custom.ChassisController;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisFullSpin extends InjectedCommand implements ChassisController {
	protected final ChassisMove move;
	
	public ChassisFullSpin(Command previous) {
		super(previous, 2D);
		move = new ChassisMove(RobotMap.Component.chassis, this);
	}
	
	public ChassisFullSpin(Command previous, String name) {
		super(previous, name, 2D);
		move = new ChassisMove(RobotMap.Component.chassis, this);
	}
	
	public ChassisFullSpin(Command previous, double timeout) {
		super(previous, timeout);
		move = new ChassisMove(RobotMap.Component.chassis, this);
	}
	
	public ChassisFullSpin(Command previous, String name, double timeout) {
		super(previous, name, timeout);
		move = new ChassisMove(RobotMap.Component.chassis, this);
	}
	
	@Override
	protected void onInitialize() {
		move.initialize();
		move.start();
		LogKitten.v("ChassisFullSpin initialized");
	}
	
	@Override
	protected void execute() {
		if (isTimedOut() || move.isFinished()) {
			end();
		}
		LogKitten.d("ChassisFullSpin executing");
	}
	
	@Override
	protected void onInterrupted() {
		move.cancel();
	}
	
	@Override
	protected void onEnd() {
		move.end();
	}
	
	@Override
	protected boolean isFinished() {
		return move.isFinished() || isTimedOut();
	}
	
	@Override
	public double getX() {
		return 0;
	}
	
	@Override
	public double getY() {
		return 0;
	}
	
	@Override
	public double getTurnSpeed() {
		return 1;
	}
}
