package org.usfirst.frc4904.standard.humaninput;


import org.usfirst.frc4904.standard.custom.Named;

/**
 * Operator specifc version of HumanInterface
 *
 */
public abstract class Operator extends HumanInput implements Named {
	public Operator(String name) {
		super(name);
	}
}
