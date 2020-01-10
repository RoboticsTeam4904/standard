package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SingleOp extends CommandBase {
	protected Runnable op;

	public SingleOp(Runnable op) {
		this.op = op;
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
