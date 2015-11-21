package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorEncoderSet;
import org.usfirst.frc4904.standard.commands.motor.MotorSet;
import org.usfirst.frc4904.standard.humaninterface.Driver;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.EncodedMotor;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChassisMove extends CommandGroup {
	private final MotorSet[] motorSpins;
	private double[] motorSpeeds;
	private final Chassis chassis;
	private final Driver driver;
	private final double xScale;
	private final double yScale;
	private final double turnScale;
	
	// private final LogKitten logger;
	public ChassisMove(Chassis chassis, Driver driver, double xScale, double yScale, double turnScale) {
		super("ChassisMove");
		requires(chassis);
		this.chassis = chassis;
		this.driver = driver;
		Motor[] motors = this.chassis.getMotors();
		this.motorSpins = new MotorSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			motorSpins[i] = new MotorSet(motors[i]);
			addParallel(motorSpins[i]);
		}
		this.xScale = xScale;
		this.yScale = yScale;
		this.turnScale = turnScale;
		LogKitten.v("ChassisMove created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
	}
	
	public ChassisMove(Chassis chassis, Driver driver) {
		this(chassis, driver, 1.0, 1.0, 1.0);
	}
	
	public ChassisMove(Chassis chassis, Driver driver, double xScale, double yScale, double turnScale, boolean encode) {
		super("ChassisMoveEncodeded");
		requires(chassis);
		this.chassis = chassis;
		this.driver = driver;
		Motor[] motors = this.chassis.getMotors();
		this.motorSpins = new MotorEncoderSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			if (motors[i] instanceof EncodedMotor && encode) {
				EncodedMotor motor = (EncodedMotor) motors[i];
				motorSpins[i] = new MotorEncoderSet(motor);
			} else {
				motorSpins[i] = new MotorSet(motors[i]);
			}
			addParallel(motorSpins[i]);
		}
		this.xScale = xScale;
		this.yScale = yScale;
		this.turnScale = turnScale;
		LogKitten.v("ChassisMove created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
	}
	
	public ChassisMove(Chassis chassis, Driver driver, boolean encode) {
		this(chassis, driver, 1.0, 1.0, 1.0, encode);
	}
	
	protected void initialize() {
		LogKitten.v("ChassisMove initialized");
	}
	
	protected void execute() {
		chassis.move2dc(driver.getX(), driver.getY(), driver.getTurnSpeed());
		motorSpeeds = chassis.getMotorSpeeds();
		String motorSpeedsString = "";
		for (int i = 0; i < motorSpins.length; i++) {
			motorSpins[i].set(motorSpeeds[i]);
			motorSpeedsString += Double.toString(motorSpeeds[i]) + " ";
		}
		LogKitten.d("ChassisMove executing");
		LogKitten.d("Motor speeds: " + motorSpeedsString);
	}
	
	protected void end() {
		LogKitten.v("ChassisMove ended");
	}
	
	protected void interrupted() {
		LogKitten.w("ChassisMove interrupted");
	}
	
	protected boolean isFinished() {
		return false;
	}
}
