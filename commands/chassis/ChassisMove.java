// package org.usfirst.frc4904.standard.commands.chassis;

// import org.usfirst.frc4904.standard.LogKitten;
// import org.usfirst.frc4904.standard.commands.motor.MotorSet;
// import org.usfirst.frc4904.standard.custom.ChassisController;
// import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
// import org.usfirst.frc4904.standard.subsystems.motor.Motor;
// import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// /**
//  * 
//  * This command moves the chassis. It move based on the driver class. Note that
//  * it supports all types of chassis. The chassis is used to calculate the motor
//  * movement. The command works by creating a movement command for each motor.
//  * This is the best way to handle this because it allows each motor to be a full
//  * subsystem.
//  */
// public class ChassisMove extends ParallelCommandGroup {
// 	protected final MotorSet[] motorSpins;
// 	protected double[] motorSpeeds;
// 	protected final Motor[] motors;
// 	protected final boolean usePID;
// 	protected final Chassis chassis;
// 	protected final ChassisController controller;

// 	/**
// 	 *
// 	 * @param name
// 	 * @param chassis    The robot's Chassis.
// 	 * @param controller A ChassisController to control the Chassis, such as a
// 	 *                   Driver or autonomous routine.
// 	 * @param usePID     Whether to enable PID using any SensorMotors that Chassis
// 	 *                   has.
// 	 */
// 	public ChassisMove(String name, Chassis chassis, ChassisController controller, boolean usePID) {
// 		super();
// 		this.chassis = chassis;
// 		this.controller = controller;
// 		this.usePID = usePID;
// 		addRequirements(chassis);
// 		setName(name);
// 		motors = chassis.getMotors();
// 		motorSpins = new MotorSet[motors.length];
// 		for (int i = 0; i < motors.length; i++) {
// 			motorSpins[i] = new MotorSet(motors[i].getName(), motors[i]);
// 		}
// 		addCommands(motorSpins);
// 	}

// 	/**
// 	 * 
// 	 * @param name
// 	 * @param chassis    The robot's chassis.
// 	 * @param controller A ChassisController to control the Chassis, such as a
// 	 *                   Driver or autonomous routine.
// 	 */
// 	public ChassisMove(String name, Chassis chassis, ChassisController controller) {
// 		this(name, chassis, controller, false);
// 	}

// 	/**
// 	 *
// 	 * @param chassis    The robot's Chassis.
// 	 * @param controller A ChassisController to control the Chassis, such as a
// 	 *                   Driver or autonomous routine.
// 	 * @param usePID     Whether to enable PID using any SensorMotors that Chassis
// 	 *                   has.
// 	 */
// 	public ChassisMove(Chassis chassis, ChassisController controller, boolean usePID) {
// 		this("Chassis Move", chassis, controller, usePID);
// 	}

// 	/**
// 	 * 
// 	 * 
// 	 * @param chassis    The robot's chassis.
// 	 * @param controller A ChassisController to control the Chassis, such as a
// 	 *                   Driver or autonomous routine.
// 	 * 
// 	 */
// 	public ChassisMove(Chassis chassis, ChassisController controller) {
// 		this("Chassis Move", chassis, controller);
// 	}

// 	/**
// 	 * 
// 	 * VelocitySensorMotors will attempt to very precisely achieve the speed set by
// 	 * this command when PID is enabled PositionSensorMotors will either attempt to
// 	 * maintain their previous position, or worse, will try to move to somewhere
// 	 * between -1.0 and 1.0, which is probably not the correct position regardless
// 	 * of the scaling.
// 	 * 
// 	 */
// 	@Override
// 	public void initialize() {
// 		super.initialize();
// 		for (Motor motor : motors) {
// 			if (motor instanceof VelocitySensorMotor) {
// 				if (usePID) {
// 					((VelocitySensorMotor) motor).enableMotionController();
// 				} else {
// 					((VelocitySensorMotor) motor).disableMotionController();
// 				}
// 			}
// 		}
// 		LogKitten.v("ChassisMove initialized");
// 	}

// 	@Override
// 	public void execute() {
// 		super.execute();
// 		chassis.moveCartesian(controller.getX(), controller.getY(), controller.getTurnSpeed());
// 		motorSpeeds = chassis.getMotorSpeeds();
// 		StringBuilder motorSpeedsString = new StringBuilder();
// 		motorSpeedsString.append("Motor speeds:");
// 		for (int i = 0; i < motorSpins.length; i++) {
// 			LogKitten.d(Double.toString(motorSpeeds[i]));
// 			motorSpins[i].set(motorSpeeds[i]);
// 			motorSpeedsString.append(' ');
// 			motorSpeedsString.append(motorSpeeds[i]);
// 		}
// 		LogKitten.d("ChassisMove executing");
// 		LogKitten.d(motorSpeedsString.toString());
// 	}

// 	@Override
// 	public boolean isFinished() {
// 		return false;
// 	}

// 	/**
// 	 * It's important to stop motor spins before the ChassisMove command stops.
// 	 * Otherwise, the spins might briefly (for one tick) use the previously set
// 	 * values if the ChassisMove command is reused.
// 	 */
// 	protected void stopMotorSpins() {
// 		for (int i = 0; i < motors.length; i++) {
// 			motorSpins[i].set(0);
// 		}
// 	}

// 	@Override
// 	public void end(boolean interrupted) {
// 		super.end(interrupted);
// 		stopMotorSpins();

// 		if (interrupted) {
// 			LogKitten.w("ChassisMove interrupted");
// 		} else {
// 			LogKitten.v("ChassisMove ended");
// 		}

// 	}
// }
