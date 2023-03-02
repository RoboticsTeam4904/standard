// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package org.usfirst.frc4904.standard.subsystems.chassis;
// // WAS PID SOURCE
// import org.usfirst.frc4904.standard.subsystems.motor.MotorSubsystem;
// import org.usfirst.frc4904.standard.custom.sensors.CANTalonEncoder;
// import org.usfirst.frc4904.standard.custom.sensors.NavX;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
// import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.Subsystem;

// public class SensorDrive implements Subsystem { // Based largely on
//   // https://github.com/wpilibsuite/allwpilib/blob/master/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/ramsetecommand/subsystems/DriveSubsystem.java
//   private final TankDrive driveBase;
//   private final CANTalonEncoder leftEncoder;
//   private final CANTalonEncoder rightEncoder;
//   private final NavX gyro;
//   private final DifferentialDriveOdometry odometry;

//   /**
//    * Creates a new DriveSubsystem.
//    */
//   public SensorDrive(TankDrive driveBase, CANTalonEncoder leftEncoder, CANTalonEncoder rightEncoder, NavX gyro, Pose2d initialPose) {
//     this.driveBase = driveBase;
//     this.leftEncoder = leftEncoder;
//     this.rightEncoder = rightEncoder;
//     this.gyro = gyro;

//     resetEncoders();
//     odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance(), initialPose);
//     CommandScheduler.getInstance().registerSubsystem(this);
//   }

//   @Override
//   public void periodic() {
//     odometry.update(gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
//   }

//   /**
//    * Returns the currently-estimated pose of the robot.
//    *
//    * @return The pose.
//    */
//   public Pose2d getPose() {
//     return odometry.getPoseMeters();
//   }

//   /**
//    * Returns the current wheel speeds of the robot.
//    *
//    * @return The current wheel speeds.
//    */
//   public DifferentialDriveWheelSpeeds getWheelSpeeds() {
//     return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
//   }

//   /**
//    * Resets the odometry to the specified pose.
//    *
//    * @param pose The pose to which to set the odometry.
//    */
//   public void resetOdometry(Pose2d pose) {
//     resetEncoders();
//     odometry.resetPosition(gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance(), pose);
//   }

//   // /**
//   //  * Controls the left and right sides of the drive directly with voltages.
//   //  *
//   //  * @param leftVolts  the commanded left output
//   //  * @param rightVolts the commanded right output
//   //  */
//   // public void tankDriveVolts(double leftVolts, double rightVolts) {
//   //   MotorSubsystem[] motors = driveBase.getMotors();
//   //   if (motors.length == 2) {
//   //     driveBase.getMotors()[0].setVoltage(leftVolts);
//   //     driveBase.getMotors()[1].setVoltage(rightVolts);
//   //   } else {
//   //     driveBase.getMotors()[0].setVoltage(leftVolts);
//   //     driveBase.getMotors()[1].setVoltage(leftVolts);
//   //     driveBase.getMotors()[2].setVoltage(rightVolts);
//   //     driveBase.getMotors()[3].setVoltage(rightVolts);
//   //   }
//   // }

//   /**
//    * Resets the drive encoders to currently read a position of 0.
//    */
//   public void resetEncoders() {
//     leftEncoder.reset();
//     rightEncoder.reset();
//   }

//   /**
//    * Returns the heading of the robot.
//    *
//    * @return the robot's heading in degrees, from 180 to 180
//    */
//   public double getHeading() {
//     return gyro.getYaw() * -1;
//   }
// }
