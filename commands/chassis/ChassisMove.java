package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorSet;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command moves the chassis.
 * It move based on the driver class.
 * Note that it supports all types of chassis.
 * The chassis is used to calculate the motor movement.
 * The command works by creating a movement command for each motor.
 * This is the best way to handle this because it allows each motor to be a full subsystem.
 */
public class ChassisMove extends CommandGroup {
	protected final MotorSet[] motorSpins;
	protected double[] motorSpeeds;
	protected final Motor[] motors;
	protected final boolean usePID;
	protected final Chassis chassis;
	protected final ChassisController controller;
	
	/**
	 * @param chassis
	 *        The robot's Chassis.
	 * @param controller
	 *        A ChassisController to control the Chassis, such as a Driver or autonomous routine.
	 * @param usePID
	 *        Whether to enable PID using any SensorMotors that Chassis has.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, boolean usePID) {
		super("ChassisMove");
		requires(chassis);
		this.chassis = chassis;
		this.controller = controller;
		this.usePID = usePID;
		motors = this.chassis.getMotors();
		motorSpins = new MotorSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			motorSpins[i] = new MotorSet(motors[i]);
			addParallel(motorSpins[i]);
		}
		LogKitten.v("ChassisMove created for " + chassis.getName());
	}
	
	/**
	 * @param chassis
	 *        The robot's chassis.
	 * @param controller
	 *        A ChassisController to control the Chassis, such as a Driver or autonomous routine.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller) {
		this(chassis, controller, false);
	}
	
	@Override
	protected void initialize() {
		for (Motor motor : motors) {
			if (motor instanceof SensorMotor) {
				if (usePID) {
					((SensorMotor) motor).enablePID();
				} else {
					((SensorMotor) motor).disablePID();
				}
			}
		}
		LogKitten.v("ChassisMove initialized");
	}
	
	@Override
	protected void execute() {
		chassis.moveCartesian(controller.getX(), controller.getY(), controller.getTurnSpeed());
		motorSpeeds = chassis.getMotorSpeeds();
		String motorSpeedsString = "";
		for (int i = 0; i < motorSpins.length; i++) {
			LogKitten.d(Double.toString(motorSpeeds[i]));
			motorSpins[i].set(motorSpeeds[i]);
			motorSpeedsString += Double.toString(motorSpeeds[i]) + " ";
		}
		LogKitten.d("ChassisMove executing");
		LogKitten.d("Motor speeds: " + motorSpeedsString);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		LogKitten.v("ChassisMove ended");
	}
	
	@Override
	protected void interrupted() {
		LogKitten.w("ChassisMove interrupted");
	}
}
