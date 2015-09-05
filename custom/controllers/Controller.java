package org.usfirst.frc4904.standard.custom.controllers;


import org.usfirst.frc4904.standard.InPipable;

public interface Controller extends InPipable {
	/**
	 * Enums for the pipe
	 * 
	 *
	 */
	public enum PipeModes {
		All, XYTwist, X, Y, Twist, Fourth;
	}
}
