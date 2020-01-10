/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4904.standard.custom;

/**
 * This interface allows for PIDController to automatically read from this object.
 *
 * 
 */
public interface CustomPIDSource {
  /**
   * Set which parameter of the device you are using as a process control variable.
   *
   * @param pidSource An enum to select the parameter.
   */
  void setPIDSourceType(CustomPIDSourceType pidSource);

  /**
   * Get which parameter of the device you are using as a process control variable.
   *
   * @return the currently selected PID source parameter
   */
  CustomPIDSourceType getPIDSourceType();

  /**
   * Get the result to use in PIDController.
   *
   * @return the result to use in PIDController
   */
  double pidGet();
}
