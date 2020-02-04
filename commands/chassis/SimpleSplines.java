/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4904.standard.commands.chassis;

import java.util.List;

import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.robot.RobotMap.DriveConstants;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.chassis.SensorDrive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SimpleSplines extends SequentialCommandGroup {
  // Create a voltage constraint to ensure we don't accelerate too fast
  public static final DifferentialDriveVoltageConstraint autoVoltageConstraint = 
    new DifferentialDriveVoltageConstraint(
      new SimpleMotorFeedforward(RobotMap.DriveConstants.ksVolts, 
      RobotMap.DriveConstants.kvVoltSecondsPerMeter, 
      RobotMap.DriveConstants.kaVoltSecondsSquaredPerMeter), 
      RobotMap.DriveConstants.kDriveKinematics, 
      10);
  public static final TrajectoryConfig config =
        new TrajectoryConfig(RobotMap.AutoConstants.kMaxSpeedMetersPerSecond,
        RobotMap.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(RobotMap.DriveConstants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

  public SimpleSplines(SensorDrive robotDrive, Pose2d init_pos, Pose2d final_pos, List inter_points){
    super(new RamseteCommand(
        TrajectoryGenerator.generateTrajectory(init_pos, inter_points, final_pos, config),
        robotDrive::getPose,
        new RamseteController(RobotMap.AutoConstants.kRamseteB, RobotMap.AutoConstants.kRamseteZeta),
        new SimpleMotorFeedforward(RobotMap.DriveConstants.ksVolts,
        RobotMap.DriveConstants.kvVoltSecondsPerMeter,
        RobotMap.DriveConstants.kaVoltSecondsSquaredPerMeter),
        RobotMap.DriveConstants.kDriveKinematics,
        robotDrive::getWheelSpeeds,
        new PIDController(RobotMap.DriveConstants.kPDriveVel, 0, 0),
        new PIDController(RobotMap.DriveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        robotDrive::tankDriveVolts,
        robotDrive.getDriveBase().getMotors()
    ), new InstantCommand(() -> robotDrive.tankDriveVolts(0, 0)));
  }
}
