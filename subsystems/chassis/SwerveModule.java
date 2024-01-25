//ALWAYS FIELD RELATIVE
package org.usfirst.frc4904.standard.subsystems.chassis;

import java.util.function.Supplier;

import org.usfirst.frc4904.robot.Robot;
import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.robot.RobotMap.Metrics;
import org.usfirst.frc4904.robot.RobotMap.PID.Drive;
import org.usfirst.frc4904.robot.RobotMap.PID.Turn;
import org.usfirst.frc4904.standard.custom.motioncontrollers.ezControl;
import org.usfirst.frc4904.standard.custom.motioncontrollers.ezMotion;
import org.usfirst.frc4904.standard.custom.motorcontrollers.CANTalonFX;
import org.usfirst.frc4904.standard.custom.motorcontrollers.CustomCANSparkMax;
import org.usfirst.frc4904.standard.subsystems.motor.SparkMaxMotorSubsystem;
import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorSubsystem;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

public class SwerveModule extends SubsystemBase{
    public final CANTalonFX driveMotor;
    public final TalonMotorSubsystem driveSubsystem;
    public final CustomCANSparkMax turnMotor;
    public final SparkMaxMotorSubsystem turnSubsystem;
    public final DutyCycleEncoder encoder;
    public final SimpleMotorFeedforward driveFeedforward;
    public final SimpleMotorFeedforward turnFeedforward;
    public final Translation2d modulePosition;

    public SwerveModule(
        CANTalonFX driveMotor,
        CustomCANSparkMax turnMotor,
        DutyCycleEncoder encoder,
        Translation2d modulePosition,
        String name
    ) {
        this.driveMotor = driveMotor; //default is coast for drive, brake for turn. no voltage compensation
        this.driveSubsystem = new TalonMotorSubsystem("drive-subsystem", NeutralModeValue.Coast, 0, driveMotor);
        this.turnMotor = turnMotor;
        this.turnSubsystem = new SparkMaxMotorSubsystem("turn-subsystem", IdleMode.kBrake, 0, false, 0, turnMotor);
        this.encoder = encoder;
        encoder.setDistancePerRotation(360);
        this.driveFeedforward = new SimpleMotorFeedforward(RobotMap.PID.Drive.kS, RobotMap.PID.Drive.kV, RobotMap.PID.Drive.kA);
        this.turnFeedforward = new SimpleMotorFeedforward(RobotMap.PID.Drive.kS, RobotMap.PID.Turn.kV, RobotMap.PID.Turn.kA);
        this.modulePosition = modulePosition;
        this.setName(name);
    }
    
    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(driveMotor.getRotorPosition().getValue()*RobotMap.Metrics.Chassis.WHEEL_DIAMETER_METERS/RobotMap.Metrics.Chassis.GEAR_RATIO_DRIVE,
        new Rotation2d(getAbsoluteAngle())); //TODO: use circumference of wheel instead?
    }
        
    //CAN BE EITHER OPEN OR CLOSED-LOOP CONTROL
    //takes in target velocities and angles and returns a command that will move the module to that state
    public Command setTargetState(Supplier<SwerveModuleState> target, boolean openloop){ //does NOT take in onarrival command dealer, should dealt with when writing autons
        var cmd = new ParallelCommandGroup();
        if(openloop){
            Command cmdDrive = new InstantCommand(() -> {driveMotor.setVoltage(driveFeedforward.calculate(target.get().speedMetersPerSecond));}, driveSubsystem);
            cmd.addCommands(cmdDrive);
        }else if(Math.abs(driveMotor.get()*RobotMap.Metrics.Chassis.MAX_SPEED - target.get().speedMetersPerSecond)>.1){ //TODO: tune this tolerance
            Command cmdDrive = c_controlWheelSpeed(() -> target.get().speedMetersPerSecond);        
            cmd.addCommands(cmdDrive);
        }
        if (Math.abs(getAbsoluteAngle() - MathUtil.inputModulus(target.get().angle.getDegrees(),-180,180))>.5) { //TODO:tune this tolerence
            Command cmdTurn = c_holdWheelAngle(MathUtil.inputModulus(target.get().angle.getDegrees(),-180,180));//angle is always closed loop
            SmartDashboard.putBoolean("turning", true);
            cmd.addCommands(cmdTurn);
        } else{SmartDashboard.putBoolean("turning", false);}
        return cmd;
    }

    //CLOSED-LOOP CONTROL
    //takes in a target speed and maintains it
    public Command c_controlWheelSpeed(Supplier<Double> targetSpeed){ //untested, prob doesn't work (less likely than hold wheel angle)
        var cmd = this.run(() -> {
            var feedfoward = this.driveFeedforward.calculate(
                driveMotor.get()*Metrics.Chassis.MAX_SPEED,
                targetSpeed.get(),
                0
            );
            SmartDashboard.putNumber("feedforward", feedfoward);
            driveMotor.setVoltage(feedfoward);
        });
        cmd.addRequirements(driveSubsystem);
        cmd.setName( this.getName() + "- c_controlWheelSpeed");
        return cmd;
    }
    //takes in angle in degrees and returns a command that will hold the wheel at that angle
    //CLOSED-LOOP CONTROL
    public Command c_holdWheelAngle(double angle){ //TODO: max turn speed and acceleration are in degrees per second and degrees per second squared, might be bad
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
                double output =  turnFeedforward.calculate(velocityDeg);
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
        
        cmd.setName( this.getName() + "- c_controlTurnSpeed");
        return cmd; //is exclusively used in parallel command group, so there should NOT be an on arrival command dealer
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(driveMotor.get(), Rotation2d.fromDegrees(getAbsoluteAngle()));
    }
    //TODO: make sure this outputs correctly, as there are a few possible bad outputs it could give (i.e. getabsolutePosition() is in wrong units is in radians or rotations rather than degrees)
    public double getAbsoluteAngle(){
        //should output from 180 to negative 180
        double raw;
        if(encoder.getAbsolutePosition()>.5){
            raw = ((encoder.getAbsolutePosition()-.5)*-360);
        }
        else{
            raw = encoder.getAbsolutePosition()*360;
        }
        return raw;
        // double raw;
        // if(encoder.getDistance()>0){ 
        //     raw = encoder.getDistance()%360;}
        // else{
        //     raw = (encoder.getDistance()%360) + 360;}   
        // //raw is now from 0 to 360
        // if(raw>180){
        //     return -raw + 360;}
        // else{
        //     return -raw;}
        
        //not sure this works
        //if(encoder.getAbsolutePosition()>0){
        //    return encoder.getAbsolutePosition()/Metrics.Chassis.GEAR_RATIO_TURN % 360;} //TODO: not sure if units are correct, needs to be right or swerve wont work
        //else{
        //    return (encoder.getAbsolutePosition()/Metrics.Chassis.GEAR_RATIO_TURN % 360) + 360;}

    }   
}
