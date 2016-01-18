package org.usfirst.frc4904.standard;


import org.usfirst.frc4904.standard.commands.healthchecks.AbstractHealthcheck;
import org.usfirst.frc4904.standard.commands.healthchecks.CheckHealth;
import org.usfirst.frc4904.standard.custom.CommandSendableChooser;
import org.usfirst.frc4904.standard.custom.TypedNamedSendableChooser;
import org.usfirst.frc4904.standard.humaninterface.Driver;
import org.usfirst.frc4904.standard.humaninterface.Operator;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class CommandRobotBase extends IterativeRobot {
	protected Command teleopCommand;
	protected Command autonomousCommand;
	protected CheckHealth healthcheckCommand;
	protected CommandSendableChooser autoChooser;
	protected TypedNamedSendableChooser<Driver> driverChooser;
	protected TypedNamedSendableChooser<Operator> operatorChooser;
	
	protected void displayChoosers() {
		// Display choosers on SmartDashboard
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
		SmartDashboard.putData("Driver control scheme chooser", driverChooser);
		SmartDashboard.putData("Operator control scheme chooser", operatorChooser);
	}
	
	public void robotInit(AbstractHealthcheck... healthchecks) {
		healthcheckCommand = new CheckHealth(healthchecks);
		// Initialize choosers
		autoChooser = new CommandSendableChooser();
		driverChooser = new TypedNamedSendableChooser<Driver>();
		operatorChooser = new TypedNamedSendableChooser<Operator>();
		if (healthcheckCommand != null) {
			healthcheckCommand.start();
		}
	}
	
	public boolean isEnabledOperatorControl() {
		return isEnabled() && isOperatorControl();
	}
	
	public boolean isEnabledAutonomous() {
		return isEnabled() && isAutonomous();
	}
	
	public void disabledInit() {
		healthcheckCommand.reset();
	}
}