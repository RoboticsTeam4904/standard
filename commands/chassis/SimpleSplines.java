// Based on software from FIRST and WPI

package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.subsystems.chassis.SensorDrive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SimpleSplines extends SequentialCommandGroup {
  public SimpleSplines(SensorDrive robotDrive, Trajectory trajectory, Command nextCommand){
    super(new RamseteCommand(
        trajectory,
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

  public SimpleSplines(SensorDrive robotDrive, Trajectory trajectory){
    this(robotDrive, trajectory, new InstantCommand(() -> robotDrive.tankDriveVolts(0, 0)));
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
