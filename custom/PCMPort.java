package org.usfirst.frc4904.standard.custom;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PCMPort {
	public int pcmID;
	public int forward;
	public int reverse;

	/**
	 * Defines a piston based on two ports and a PCM number
	 * 
	 * @param pcmID
	 *        The ID of the PCM attached to the piston. Usually 0 or 1.
	 * @param forward
	 *        The forward port of the piston.
	 * @param reverse
	 *        The reverse port of the piston.
	 */
	public PCMPort(int pcmID, int forward, int reverse) { // First variable PCM number, second forward, third reverse.
		this.pcmID = pcmID;
		this.forward = forward;
		this.reverse = reverse;
	}

	public DoubleSolenoid buildDoubleSolenoid() {
		return new DoubleSolenoid(pcmID, forward, reverse);
	}
}
