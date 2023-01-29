// Based on software from FIRST and WPI

package org.usfirst.frc4904.standard.commands.chassis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class SimpleSplines extends SequentialCommandGroup {
  public SimpleSplines(SplinesDrive robotDrive, Pose2d init_pos, List<Translation2d> inter_points, Pose2d final_pos, double maxVoltage, Command nextCommand){
    
    super(
      new RamseteCommand(
        TrajectoryGenerator.generateTrajectory(
          init_pos,
          inter_points,
          new Pose2d(final_pos.getX()+init_pos.getX(), final_pos.getY()+init_pos.getY(), final_pos.getRotation()),
          new TrajectoryConfig(
            robotDrive.getAutoConstants().kMaxSpeedMetersPerSecond,
            robotDrive.getAutoConstants().kMaxAccelerationMetersPerSecondSquared
          )
            .setKinematics(robotDrive.getDriveConstants().kDriveKinematics)
            .addConstraint(
              new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                  robotDrive.getDriveConstants().ksVolts, 
                  robotDrive.getDriveConstants().kvVoltSecondsPerMeter, 
                  robotDrive.getDriveConstants().kaVoltSecondsSquaredPerMeter
                ), 
                robotDrive.getDriveConstants().kDriveKinematics, 
                maxVoltage
              )
            )
        ),
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
        robotDrive::tankDriveVolts,
        robotDrive
      ),
      nextCommand
    );
    robotDrive.resetOdometry(init_pos);

    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
      init_pos,
      inter_points,
      final_pos,
      new TrajectoryConfig(
        robotDrive.getAutoConstants().kMaxSpeedMetersPerSecond,
        robotDrive.getAutoConstants().kMaxAccelerationMetersPerSecondSquared
      )
        .setKinematics(robotDrive.getDriveConstants().kDriveKinematics)
        .addConstraint(
          new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
              robotDrive.getDriveConstants().ksVolts, 
              robotDrive.getDriveConstants().kvVoltSecondsPerMeter, 
              robotDrive.getDriveConstants().kaVoltSecondsSquaredPerMeter
            ), 
            robotDrive.getDriveConstants().kDriveKinematics, 
            maxVoltage
          )
        )
    );
    // Log the trajectory https://docs.wpilib.org/en/stable/docs/software/dashboards/glass/field2d-widget.html
    Field2d m_field = new Field2d();
    SmartDashboard.putData(m_field);
    m_field.getObject("traj").setTrajectory(trajectory);

    // // also log them individually for comparison
    // trajectory.getStates().forEach(mmm -> {
    //   SmartDashboard.putNumber("Intended Trajectory elapsed_time", mmm.timeSeconds);
    //   SmartDashboard.putNumber("Intended Trajectory velocity", mmm.velocityMetersPerSecond);
    //   SmartDashboard.putNumber("Intended Trajectory poseX", mmm.poseMeters.getX());
    //   SmartDashboard.putNumber("Intended Trajectory poseY", mmm.poseMeters.getY());
    // });

    // actually, resample the trajectory to 20ms intervals to match the timed logging 
    ArrayList<ArrayList> TrajectoryData = new ArrayList<ArrayList>(); 
    for (double elapsed=0; elapsed<trajectory.getTotalTimeSeconds(); elapsed += 0.02) {
      var mmm = trajectory.sample(elapsed);
      SmartDashboard.putNumber("Intended Trajectory elapsed_time", mmm.timeSeconds);
      SmartDashboard.putNumber("Intended Trajectory velocity", mmm.velocityMetersPerSecond);
      SmartDashboard.putNumber("Intended Trajectory poseX", mmm.poseMeters.getX());
      SmartDashboard.putNumber("Intended Trajectory poseY", mmm.poseMeters.getY());
      TrajectoryData.add(new ArrayList<Double>(Arrays.asList(mmm.timeSeconds, mmm.velocityMetersPerSecond, mmm.poseMeters.getX(), mmm.poseMeters.getY())));
    }
    try {
      FileWriter writer = new FileWriter("/home/lvuser/trajectory.csv");
      for (ArrayList<Double> row : TrajectoryData) {
        writer.write(String.join(",", row.stream().map(Object::toString).collect(Collectors.toList())));
        writer.write("\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
