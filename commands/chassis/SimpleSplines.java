// Based on software from FIRST and WPI

package org.usfirst.frc4904.standard.commands.chassis;

import java.util.List;

import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.subsystems.chassis.SensorDrive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SimpleSplines extends SequentialCommandGroup {
  public SimpleSplines(SensorDrive robotDrive, Pose2d init_pos, List<Translation2d> inter_points, Pose2d final_pos, AutoConstants autoConstants, DriveConstants driveConstants){
    super(new RamseteCommand(
        TrajectoryGenerator.generateTrajectory(init_pos, inter_points, final_pos, new TrajectoryConfig(autoConstants.kMaxSpeedMetersPerSecond,
        autoConstants.kMaxAccelerationMetersPerSecondSquared)
            .setKinematics(driveConstants.kDriveKinematics)
            .addConstraint(new DifferentialDriveVoltageConstraint(
              new SimpleMotorFeedforward(driveConstants.ksVolts, 
              driveConstants.kvVoltSecondsPerMeter, 
              driveConstants.kaVoltSecondsSquaredPerMeter), 
              driveConstants.kDriveKinematics, 
              10))),
        robotDrive::getPose,
        new RamseteController(autoConstants.kRamseteB, autoConstants.kRamseteZeta),
        new SimpleMotorFeedforward(driveConstants.ksVolts,
        driveConstants.kvVoltSecondsPerMeter,
        driveConstants.kaVoltSecondsSquaredPerMeter),
        driveConstants.kDriveKinematics,
        robotDrive::getWheelSpeeds,
        new PIDController(driveConstants.kPDriveVel, 0, 0),
        new PIDController(driveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        robotDrive::tankDriveVolts, robotDrive), new InstantCommand(() -> robotDrive.tankDriveVolts(0, 0)));
  }

  public static class AutoConstants {
    public double kMaxSpeedMetersPerSecond;
		public double kMaxAccelerationMetersPerSecondSquared;
		public double kRamseteB;
		public double kRamseteZeta;

    public AutoConstants(double kMaxSpeedMetersPerSecond, double kMaxAccelerationMetersPerSecondSquared, double kRamseteB, double kRamseteZeta) {
      this.kMaxSpeedMetersPerSecond = kMaxSpeedMetersPerSecond;
      this.kMaxAccelerationMetersPerSecondSquared = kMaxAccelerationMetersPerSecondSquared;
      this.kRamseteB = kRamseteB;
      this.kRamseteZeta = kRamseteZeta;
    }
  }

  public static class DriveConstants {
    public double ksVolts = 0.319;
		public double kvVoltSecondsPerMeter = 4.71;
    public double kaVoltSecondsSquaredPerMeter = 0.208;
    public double kTrackwidthMeters = 0.6161858257789627; //0.5842
		public DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);;
    public double kPDriveVel = 7.28;
    
    public DriveConstants(double ksVolts, double kvVoltSecondsPerMeter, double kaVoltSecondsSquaredPerMeter, double kTrackwidthMeters, DifferentialDriveKinematics kDriveKinematics, double kPDriveVel){
      this.ksVolts = ksVolts;
      this.kvVoltSecondsPerMeter = kvVoltSecondsPerMeter;
      this.kaVoltSecondsSquaredPerMeter = kaVoltSecondsSquaredPerMeter;
      this.kTrackwidthMeters = kTrackwidthMeters;
      this.kDriveKinematics = kDriveKinematics;
      this.kPDriveVel = kPDriveVel;
    }
  }
}
