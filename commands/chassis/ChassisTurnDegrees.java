package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisTurnDegrees extends Command implements ChassisController {
	protected final ChassisMove move;
	protected double initialAngle;
	protected final double finalAngle;
	protected final MotionController motionController;
	protected final IMU imu;
	
	public ChassisTurnDegrees(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
		move = new ChassisMove(chassis, this);
		this.finalAngle = -((finalAngle + 360) % 360 - 180);
		this.imu = imu;
		this.motionController = motionController;
	}
	
	@Override
	public double getX() {
		return 0.0;
	}
	
	@Override
	public double getY() {
		return 0.0;
	}
	
	@Override
	public double getTurnSpeed() {
		return motionController.get();
	}
	
	@Override
	protected void initialize() {
		move.start();
		initialAngle = imu.getYaw();
		motionController.reset();
	}
	
	@Override
	protected void execute() {
		motionController.setSetpoint(((finalAngle + initialAngle) + 360) % 360 - 180);
	}
	
	@Override
	protected boolean isFinished() {
		return motionController.onTarget();
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