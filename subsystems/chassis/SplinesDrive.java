/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4904.standard.subsystems.chassis;

import java.util.List;

import com.ctre.phoenix.sensors.CANCoder;

import org.usfirst.frc4904.standard.commands.chassis.SimpleSplines;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDrive;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SplinesDrive extends SensorDrive {
  private final SimpleSplines.SplineAutoConstants autoConstants;
  private final SimpleSplines.SplineDriveConstants driveConstants;
  private TrajectoryConfig pathConfig;

  /**
   * Creates a new DriveSubsystem.
   */
  public SplinesDrive(TankDrive driveBase, SimpleSplines.SplineAutoConstants autoConstants, SimpleSplines.SplineDriveConstants driveConstants, CANCoder leftEncoder, CANCoder rightEncoder, IMU gyro) {
    super(driveBase,leftEncoder, rightEncoder, gyro);
    this.autoConstants = autoConstants;
    this.driveConstants = driveConstants;
    configuratePath(10);
  }

  public SimpleSplines.SplineAutoConstants getAutoConstants(){
    return autoConstants;
  }

  public SimpleSplines.SplineDriveConstants getDriveConstants(){
    return driveConstants;
  }

    /**
   * Sets the pathConfig based on the maxVoltage allowed.
   * 
   * @param maxVoltage the max voltage to be allowed in the chassis.
   */
  public void configuratePath(double maxVoltage){
    pathConfig = new TrajectoryConfig(autoConstants.MAX_SPEED, autoConstants.MAX_ACCEL)
        .setKinematics(driveConstants.DRIVE_KINEMATICS)
        .addConstraint(new DifferentialDriveVoltageConstraint(
          new SimpleMotorFeedforward(driveConstants.KS, 
          driveConstants.KV, 
          driveConstants.KA), 
          driveConstants.DRIVE_KINEMATICS, 
          maxVoltage));
  }

  /**
   * Generates a trajectory using clamped cubic splines, allowing the input of a starting and ending Pose2d and intermediate Translation2d Points.
   * @param init_pos the initial pose to start at - unless otherwise specified, RamseteCommand will automatically assume this to be the robot's initial position.
   * @param inter_points the intermediate (x,y) Translation2d waypoints. Can create using List.of().
   * @param final_pos the final pose of the robot.
   * @return the trajectory.
   */
  public Trajectory generateSimpleTrajectory(Pose2d init_pos, List<Translation2d> inter_points, Pose2d final_pos){
    return TrajectoryGenerator.generateTrajectory(init_pos, inter_points, final_pos, pathConfig);
  }

  /**
   * Generates a trajectory using quintic splines, allowing the input of a list of Pose2d waypoints.
   * @param waypoints the waypoints that the spline should intersect.
   * @return the trajectory.
   */
  public Trajectory generateQuinticTrajectory(List<Pose2d> waypoints){
    return TrajectoryGenerator.generateTrajectory(waypoints, pathConfig);
  }
}
