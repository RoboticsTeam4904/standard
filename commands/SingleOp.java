package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SingleOp extends CommandBase {
	protected Runnable op;

	public SingleOp(String name, Runnable op) {
		super();
		setName(name);
		this.op = op;
	}

	public SingleOp(Runnable op) {
		this("SingleOp", op);
	}

	@Override
	public void initialize() {
		op.run();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
