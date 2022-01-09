// Based on software from FIRST and WPI

package org.usfirst.frc4904.standard.commands.chassis;

import java.util.List;

import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.subsystems.chassis.SplinesDrive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SimpleSplines extends SequentialCommandGroup {
  public SimpleSplines(SplinesDrive robotDrive, Pose2d init_pos, List<Translation2d> inter_points, Pose2d final_pos, double maxVoltage, Command nextCommand){
    super(new RamseteCommand(
        TrajectoryGenerator.generateTrajectory(init_pos, inter_points, final_pos, new TrajectoryConfig(robotDrive.getAutoConstants().kMaxSpeedMetersPerSecond,
        robotDrive.getAutoConstants().kMaxAccelerationMetersPerSecondSquared)
            .setKinematics(robotDrive.getDriveConstants().kDriveKinematics)
            .addConstraint(new DifferentialDriveVoltageConstraint(
              new SimpleMotorFeedforward(robotDrive.getDriveConstants().ksVolts, 
              robotDrive.getDriveConstants().kvVoltSecondsPerMeter, 
              robotDrive.getDriveConstants().kaVoltSecondsSquaredPerMeter), 
              robotDrive.getDriveConstants().kDriveKinematics, 
              maxVoltage))),
        robotDrive::getPose,
        new RamseteController(robotDrive.getAutoConstants().kRamseteB, robotDrive.getAutoConstants().kRamseteZeta),
        new SimpleMotorFeedforward(robotDrive.getDriveConstants().ksVolts,
        robotDrive.getDriveConstants().kvVoltSecondsPerMeter,
        robotDrive.getDriveConstants().kaVoltSecondsSquaredPerMeter),
        robotDrive.getDriveConstants().kDriveKinematics,
        robotDrive::getWheelSpeeds,
        new PIDController(robotDrive.getDriveConstants().kPDriveVel, 0, 0),
        new PIDController(robotDrive.getDriveConstants().kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        robotDrive::tankDriveVolts, robotDrive), nextCommand);
  } 

  public SimpleSplines(SplinesDrive robotDrive, Pose2d init_pos, List<Translation2d> inter_points, Pose2d final_pos, double maxVoltage){
    this(robotDrive, init_pos, inter_points, final_pos, maxVoltage, new InstantCommand(() -> robotDrive.tankDriveVolts(0, 0)));
  }

  public SimpleSplines(SplinesDrive robotDrive, Pose2d init_pos, List<Translation2d> inter_points, Pose2d final_pos){
    this(robotDrive, init_pos, inter_points, final_pos, 10);
  }

  public SimpleSplines(SplinesDrive robotDrive, Pose2d init_pos, Pose2d final_pos){
    this(robotDrive, init_pos, List.of(), final_pos);
  }


  /**
   * Class to store autonomous constants used for Ramsete Pathing.
   */
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

  /**
   * Class to store drive constants used for Ramsete pathing.
   */
  public static class DriveConstants {
    public double ksVolts;
		public double kvVoltSecondsPerMeter;
    public double kaVoltSecondsSquaredPerMeter;
    public double kTrackwidthMeters;
		public DifferentialDriveKinematics kDriveKinematics;
    public double kPDriveVel;
    
    public DriveConstants(double ksVolts, double kvVoltSecondsPerMeter, double kaVoltSecondsSquaredPerMeter, double kTrackwidthMeters, double kPDriveVel){
      this.ksVolts = ksVolts;
      this.kvVoltSecondsPerMeter = kvVoltSecondsPerMeter;
      this.kaVoltSecondsSquaredPerMeter = kaVoltSecondsSquaredPerMeter;
      this.kTrackwidthMeters = kTrackwidthMeters;
      this.kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
      this.kPDriveVel = kPDriveVel;
    }
  }
}
