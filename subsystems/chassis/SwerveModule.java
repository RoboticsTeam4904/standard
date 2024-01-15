//ALWAYS FIELD RELATIVE
package org.usfirst.frc4904.standard.subsystems.chassis;

import java.util.function.Supplier;

import org.usfirst.frc4904.robot.Robot;
import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.robot.RobotMap.PID.Drive;
import org.usfirst.frc4904.robot.RobotMap.PID.Turn;
import org.usfirst.frc4904.standard.custom.motioncontrollers.ezControl;
import org.usfirst.frc4904.standard.custom.motioncontrollers.ezMotion;
import org.usfirst.frc4904.standard.custom.motorcontrollers.CANTalonFX;
import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorSubsystem;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class SwerveModule extends SubsystemBase{
    public final CANTalonFX driveMotor;
    public final TalonMotorSubsystem driveSubsystem;
    public final CANTalonFX turnMotor;
    public final TalonMotorSubsystem turnSubsystem;
    public final DutyCycleEncoder encoder;
    public final SimpleMotorFeedforward driveFeedforward;
    public final SimpleMotorFeedforward turnFeedforward;
    public final Translation2d modulePosition;

    public SwerveModule(
        CANTalonFX driveMotor,
        CANTalonFX turnMotor,
        DutyCycleEncoder encoder,
        Translation2d modulePosition
    ) {
        this.driveMotor = driveMotor; //default is coast for drive, brake for turn. no voltage compensation
        this.driveSubsystem = new TalonMotorSubsystem("drive-subsystem", NeutralMode.Coast, 0, driveMotor);
        this.turnMotor = turnMotor;
        this.turnSubsystem = new TalonMotorSubsystem("turn-subsystem", NeutralMode.Brake, 0, turnMotor);
        this.encoder = encoder;
        this.driveFeedforward = new SimpleMotorFeedforward(0, RobotMap.PID.Drive.kV, RobotMap.PID.Drive.kA);
        this.turnFeedforward = new SimpleMotorFeedforward(0, RobotMap.PID.Turn.kV, RobotMap.PID.Turn.kA);
        this.modulePosition = modulePosition;
    }
    
    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(driveMotor.getSelectedSensorPosition()*RobotMap.Metrics.Chassis.WHEEL_DIAMETER_METERS/RobotMap.Metrics.Chassis.EncoderTicksPerRevolution/RobotMap.Metrics.Chassis.GEAR_RATIO,
        new Rotation2d(getAbsoluteAngle()));
    }
        
    //CAN BE EITHER OPEN OR CLOSED-LOOP CONTROL
    //takes in target velocities and angles and returns a command that will move the module to that state
    public Command setTargetState(Supplier<SwerveModuleState> target, boolean openloop){ //does NOT take in onarrival command dealer, should dealt with when writing autons
        var cmd = new ParallelCommandGroup();
        if(openloop){
            Command cmdDrive = new InstantCommand(() -> {driveMotor.setVoltage(target.get().speedMetersPerSecond/RobotMap.Metrics.Chassis.MAX_SPEED);}, driveSubsystem);
            cmd.addCommands(cmdDrive);
        }else if(driveMotor.get()*RobotMap.Metrics.Chassis.MAX_SPEED != target.get().speedMetersPerSecond){ //if the drive motor is not at the target speed, run the drive motor
            Command cmdDrive = c_controlWheelSpeed(target.get().speedMetersPerSecond);        
            cmd.addCommands(cmdDrive);
        }
        if (getAbsoluteAngle() != target.get().angle.getDegrees()) { //angle is always closed loop
            Command cmdTurn = c_holdWheelAngle(target.get().angle.getDegrees());
            cmd.addCommands(cmdTurn);
        }
        return cmd;
    }

    //CLOSED-LOOP CONTROL
    //takes in a target speed and maintains it
    public Command c_controlWheelSpeed(double targetSpeed){ //DOES NOT CURRENTLY DO ANYTHING!!!
        TrapezoidProfile Driveprofile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(RobotMap.Metrics.Chassis.MAX_SPEED, RobotMap.Metrics.Chassis.MAX_ACCELERATION),
            new TrapezoidProfile.State(driveMotor.get()*RobotMap.Metrics.Chassis.MAX_SPEED,0),
            new TrapezoidProfile.State(targetSpeed, driveMotor.get()*RobotMap.Metrics.Chassis.MAX_SPEED)
        );
        var cmd = new ParallelCommandGroup();
        return cmd;

    }
    //takes in angle in degrees and returns a command that will hold the wheel at that angle
    //CLOSED-LOOP CONTROL
    public Command c_holdWheelAngle(double angle){
        TrapezoidProfile Turnprofile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(RobotMap.Metrics.Chassis.MAX_TURN_SPEED, RobotMap.Metrics.Chassis.MAX_TURN_ACCELERATION),
            new TrapezoidProfile.State(angle,0),
            new TrapezoidProfile.State(getAbsoluteAngle(), turnMotor.get()*RobotMap.Metrics.Chassis.MAX_TURN_SPEED)
        );

        ezControl controller = new ezControl(
            RobotMap.PID.Turn.kP,
            RobotMap.PID.Turn.kI,
            RobotMap.PID.Turn.kD,
            (position, velocityDeg) -> {
                double output =  turnFeedforward.calculate(
                    turnMotor.get()*RobotMap.Metrics.Chassis.MAX_TURN_SPEED,
                    velocityDeg, 0);
                return output;
            }
        );
        
        var cmd = new ezMotion(controller, 
        () -> this.getAbsoluteAngle(), 
        (double volts) -> {turnMotor.setVoltage(volts);}, 
        (double t) ->  {
            return new Pair<Double, Double>(Turnprofile.calculate(t).position, Turnprofile.calculate(t).velocity);
        },
        turnSubsystem);

        return cmd; //is exclusively used in parallel command group, so there should NOT be an on arrival command dealer
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(driveMotor.get(), Rotation2d.fromDegrees(getAbsoluteAngle()));
    }

    public double getAbsoluteAngle(){
        return encoder.getAbsolutePosition() % 360; //TODO: not sure if units are correct, needs to be right or swerve wont work
    }
}
