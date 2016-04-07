package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisMotionProfileDistance extends Command implements ChassisController {
	private final ChassisMove chassisMove;
	private final MotionController motionController;
	private final double distance;
	private final CustomEncoder[] encoders;
	
	public ChassisMotionProfileDistance(Chassis chassis, double distance, MotionController motionController, CustomEncoder... encoders) {
		chassisMove = new ChassisMove(chassis, this, false);
		this.motionController = motionController;
		this.distance = distance;
		this.encoders = encoders;
	}
	
	@Override
	public void initialize() {
		chassisMove.start();
		motionController.reset();
		for (CustomEncoder encoder : encoders) {
			encoder.reset();
		}
	}
	
	@Override
	public double getX() {
		return 0;
	}
	
	@Override
	public double getY() {
		double speed = motionController.get();
		LogKitten.d("MotionProfileSpeed: " + speed);
		return speed;
	}
	
	@Override
	public double getTurnSpeed() {
		return 0;
	}
	
	@Override
	protected void end() {
		chassisMove.cancel();
	}
	
	@Override
	protected void execute() {
		motionController.setSetpoint(distance);
	}
	
	@Override
	protected void interrupted() {
		end();
	}
	
	@Override
	protected boolean isFinished() {
		return motionController.onTarget();
	}
}
