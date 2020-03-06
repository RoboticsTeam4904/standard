/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4904.standard.subsystems.chassis;

import com.ctre.phoenix.sensors.CANCoder;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.chassis.SimpleSplines;
import org.usfirst.frc4904.standard.custom.CustomPIDSourceType;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.PIDSensor;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDrive;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SensorDrive implements Subsystem, PIDSensor { // Based largely on
  // https://github.com/wpilibsuite/allwpilib/blob/master/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/ramsetecommand/subsystems/DriveSubsystem.java
  private final TankDrive driveBase;
  private final CANCoder leftEncoder;
  private final CANCoder rightEncoder;
  private final IMU gyro;
  private final DifferentialDriveOdometry odometry;
  private CustomPIDSourceType sensorType;

  /**
   * Creates a new DriveSubsystem.
   */
  public SensorDrive(TankDrive driveBase, CANCoder leftEncoder, CANCoder rightEncoder, IMU gyro,
      CustomPIDSourceType sensorType) {
    this.driveBase = driveBase;
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
    this.gyro = gyro;
    setCustomPIDSourceType(sensorType);

    resetEncoders();
    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    CommandScheduler.getInstance().registerSubsystem(this);
  }

  public SensorDrive(TankDrive driveBase, CANCoder leftEncoder, CANCoder rightEncoder, IMU gyro) {
    this(driveBase, leftEncoder, rightEncoder, gyro, CustomPIDSourceType.kDisplacement);
  }

  @Override
  public void periodic() {
    odometry.update(Rotation2d.fromDegrees(getHeading()), leftEncoder.getPosition(), rightEncoder.getPosition());
  }

  @Override
  public void setCustomPIDSourceType(CustomPIDSourceType sensorType) {
    this.sensorType = sensorType;
  }

  @Override
  public CustomPIDSourceType getCustomPIDSourceType() {
    return sensorType;
  }

  @Override
  public double pidGetSafely() throws InvalidSensorException {
    return getPose().getRotation().getDegrees();
  }

  @Override
  public double pidGet() {
    try {
      return pidGetSafely();
    } catch (Exception e) {
      LogKitten.ex(e);
      return 0;
    }
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoder.getVelocity(), rightEncoder.getVelocity());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    Motor[] motors = driveBase.getMotors();
    if (motors.length == 2) {
      driveBase.getMotors()[0].setVoltage(leftVolts);
      driveBase.getMotors()[1].setVoltage(rightVolts);
    } else {
      driveBase.getMotors()[0].setVoltage(leftVolts);
      driveBase.getMotors()[1].setVoltage(leftVolts);
      driveBase.getMotors()[2].setVoltage(rightVolts);
      driveBase.getMotors()[3].setVoltage(rightVolts);
    }
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from 180 to 180
   */
  public double getHeading() {
    return gyro.getYaw() * -1;
  }
}
