package org.usfirst.frc4904.standard.humaninterface;


import org.usfirst.frc4904.standard.custom.Named;

/**
 * Operator specifc version of HumanInterface
 *
 */
public abstract class Operator extends HumanInterface implements Named {
	public Operator(String name) {
		super(name);
	}
}
