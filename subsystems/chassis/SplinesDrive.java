/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4904.standard.subsystems.chassis;

import com.ctre.phoenix.sensors.CANCoder;

import org.usfirst.frc4904.standard.commands.chassis.SimpleSplines;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDrive;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SplinesDrive extends SensorDrive {
  private final SimpleSplines.AutoConstants autoConstants;
  private final SimpleSplines.DriveConstants driveConstants;

  /**
   * Creates a new DriveSubsystem.
   */
  public SplinesDrive(TankDrive driveBase, SimpleSplines.AutoConstants autoConstants, SimpleSplines.DriveConstants driveConstants, CANCoder leftEncoder, CANCoder rightEncoder, IMU gyro) {
    super(driveBase,leftEncoder, rightEncoder, gyro);
    this.autoConstants = autoConstants;
    this.driveConstants = driveConstants;
  }

  public SimpleSplines.AutoConstants getAutoConstants(){
    return autoConstants;
  }

  public SimpleSplines.DriveConstants getDriveConstants(){
    return driveConstants;
  }
}
