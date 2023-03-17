package org.usfirst.frc4904.standard.custom.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class CustomCommandXbox extends CommandXboxController{
    private final double deadZoneSize;
    private final XboxController m_hid;
    public CustomCommandXbox(int port, double deadZoneSize){
        super(port);
        m_hid = new XboxController(port);
        this.deadZoneSize = deadZoneSize;
    }

    public double getLeftX() {
        return applyDeadzone(m_hid.getLeftX(),deadZoneSize);
      }
    
      /**
       * Get the X axis value of right side of the controller.
       *
       * @return The axis value.
       */
      public double getRightX() {
        return applyDeadzone(m_hid.getRightX(),deadZoneSize);
      }
    
      /**
       * Get the Y axis value of left side of the controller.
       *
       * @return The axis value.
       */
      public double getLeftY() {
        return applyDeadzone(m_hid.getLeftY(),deadZoneSize);
      }
    
      /**
       * Get the Y axis value of right side of the controller.
       *
       * @return The axis value.
       */
      public double getRightY() {
        return applyDeadzone(m_hid.getRightY(),deadZoneSize);
      }
      
      public double applyDeadzone(double input, double deadZoneSize){
        final double negative;
		double deadZoneSizeClamp = deadZoneSize;
		double adjusted;
		if (deadZoneSizeClamp < 0 || deadZoneSizeClamp >= 1) {
			deadZoneSizeClamp = 0; // Prevent any weird errors
		}
		negative = input < 0 ? -1 : 1;
		adjusted = Math.abs(input) - deadZoneSizeClamp; // Subtract the deadzone from the magnitude
		adjusted = adjusted < 0 ? 0 : adjusted; // if the new input is negative, make it zero
		adjusted = adjusted / (1 - deadZoneSizeClamp); // Adjust the adjustment so it can max at 1
		return negative * adjusted;
      }
}
