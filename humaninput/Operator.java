package org.usfirst.frc4904.standard.humaninput;


import org.usfirst.frc4904.standard.custom.Nameable;

/**
 * Operator specifc version of HumanInterface
 *
 */
public abstract class Operator extends HumanInput implements Nameable {
	public Operator(String name) {
		super(name);
	}
}
