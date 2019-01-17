package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.Compressor;

public class InterruptCompressor extends HealthProtectCommand {
	protected final Compressor compressor;

	public InterruptCompressor(Compressor compressor) {
		this.compressor = compressor;
	}

	@Override
	protected void initialize() {
		compressor.stop();
	}

	@Override
	public void reset() {
		compressor.start();
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return true; // Once we stop the compressor, we are done
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
