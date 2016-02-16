package org.usfirst.frc4904.standard;


import org.usfirst.frc4904.standard.commands.healthchecks.AbstractHealthCheck;
import org.usfirst.frc4904.standard.commands.healthchecks.CheckHealth;
import org.usfirst.frc4904.standard.custom.CommandSendableChooser;
import org.usfirst.frc4904.standard.custom.TypedNamedSendableChooser;
import org.usfirst.frc4904.standard.humaninput.Driver;
import org.usfirst.frc4904.standard.humaninput.Operator;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * IterativeRobot is normally the base class for
 * CommandBased code, but we think certain features will
 * almost always be needed, so we created the
 * CommandRobotBase class. Robot should extend this
 * instead of iterative robot.
 *
 */
public abstract class CommandRobotBase extends IterativeRobot {
	protected Command teleopCommand;
	protected Command autonomousCommand;
	protected CheckHealth healthcheckCommand;
	protected CommandSendableChooser autoChooser;
	protected TypedNamedSendableChooser<Driver> driverChooser;
	protected TypedNamedSendableChooser<Operator> operatorChooser;
	
	/**
	 * This displays our choosers.
	 * The default choosers are for
	 * autonomous type, driver control,
	 * and operator control.
	 */
	protected void displayChoosers() {
		// Display choosers on SmartDashboard
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
		SmartDashboard.putData("Driver control scheme chooser", driverChooser);
		SmartDashboard.putData("Operator control scheme chooser", operatorChooser);
	}
	
	/**
	 * 
	 * This is some code that makes it
	 * a little easier to initialize the robot.
	 * It currently automatically handles
	 * healthchecks, autonomous initialization,
	 * driver initialization, and operator
	 * initializations.
	 * Note that you should still
	 * have a robotInit, but it should
	 * call super.robotInit.
	 * 
	 * @param healthchecks
	 */
	public void robotInit(AbstractHealthCheck... healthchecks) {
		if (healthchecks != null) {
			healthcheckCommand = new CheckHealth(healthchecks);
		}
		// Initialize choosers
		autoChooser = new CommandSendableChooser();
		driverChooser = new TypedNamedSendableChooser<Driver>();
		operatorChooser = new TypedNamedSendableChooser<Operator>();
		if (healthcheckCommand != null) {
			healthcheckCommand.start();
		}
	}
	
	/**
	 * 
	 * @return
	 * 		True if the robot is enabled
	 *         and is in operator control.
	 */
	public boolean isEnabledOperatorControl() {
		return isEnabled() && isOperatorControl();
	}
	
	/**
	 * 
	 * @return
	 * 		True if the robot is enabled
	 *         and is in autonomous mode.
	 */
	public boolean isEnabledAutonomous() {
		return isEnabled() && isAutonomous();
	}
	
	/**
	 * This function is called when the
	 * robot is disabled. Please ensure
	 * that if you decide to override
	 * you call super.disabledInit.
	 */
	public void disabledInit() {
		if (healthcheckCommand != null) {
			healthcheckCommand.reset();
		}
	}
}
