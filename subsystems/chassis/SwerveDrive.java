//ALWAYS FIELD RELATIVE
package org.usfirst.frc4904.standard.subsystems.chassis;

import java.util.function.Supplier;

import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorSubsystem;

import com.fasterxml.jackson.databind.Module;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase {
    
    public final SwerveModule[] modules;
    public final SwerveDriveKinematics kinematics; //module positions are contained here
    public final SwerveDriveOdometry odometry;
    protected final AHRS gyro;   
    public final Translation2d centerMassOffset; //TODO: add center of mass offset if necessary
    public Pose2d initialPose; //TODO: add initial pose
    public SwerveModulePosition[] modulePositions; 

    public SwerveDrive(SwerveModule[] modules, SwerveDriveKinematics kinematics, AHRS gyro, 
    Translation2d centerMassOffset, Pose2d initialPose) {
        this.kinematics = kinematics;
        this.modules = modules;
        this.centerMassOffset = centerMassOffset;
        this.modulePositions = new SwerveModulePosition[modules.length];
        for (int i = 0; i < modules.length; i++) {
            modulePositions[i]=modules[i].getPosition();
        }
        this.odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d(), modulePositions, initialPose); //TODO: initial pose dependds on starting position
        this.gyro = gyro;
    }

    @Override
    public void periodic(){
        odometry.update(gyro.getRotation2d(), modulePositions);
    }


    public ChassisSpeeds getSpeed(){
        return kinematics.toChassisSpeeds(getModuleStates());
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[modules.length];
        for (int i = 0; i < modules.length; i++) {
            states[i] = modules[i].getState();
        }
        return states;
    }


    //this takes in a x and y speed and a rotation speed and converts it to a target state for each module
    public Command c_drive(Supplier<Translation2d> xy, Supplier<Double> rotation, boolean openloop){
        return c_drive(()->new ChassisSpeeds(xy.get().getX(), xy.get().getY(), rotation.get()), openloop);
    }
    //takes in a chassis speed and converts it to a target state for each module
    //TODO: spinning wheel in opposite direction > unnecessarily rotating module
    public Command c_drive(Supplier<ChassisSpeeds> target, boolean openloop){
        SmartDashboard.putNumber("module 1 target turn angel from cdrive", target.get().omegaRadiansPerSecond);
        //convert chassis speeds to module states
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(target.get(), centerMassOffset);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, getSpeed(), RobotMap.Metrics.Chassis.MAX_SPEED, RobotMap.Metrics.Chassis.MAX_TRANSLATION_SPEED, RobotMap.Metrics.Chassis.MAX_TURN_SPEED);

    
        //set target states for each module
        var cmd = new InstantCommand(()->{
        for (int i = 0; i < modules.length; i++) {
            var state = states[i];
            modules[i].setTargetState(state, openloop);
        }});
        cmd.addRequirements(this);
        return cmd;
    }
}