package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisSet extends Command implements ChassisController {
	ChassisMove move;
	
	public ChassisSet(Chassis chassis) {
		move = new ChassisMove(chassis, this);
	}
	
	public double getX() {
		return 0;
	}
	
	@Override
	public double getY() {
		return 1.0;
	}
	
	public double getTurnSpeed() {
		return 0;
	}
	
	protected void initialize() {}
	
	protected void execute() {}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {}
	
	protected void interrupted() {}
}
