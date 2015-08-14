package org.usfirst.frc4904.cmdbased.custom.controllers;


import org.usfirst.frc4904.cmdbased.InPipable;

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
