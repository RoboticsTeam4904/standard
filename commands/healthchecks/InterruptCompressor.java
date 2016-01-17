package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.Compressor;

public class InterruptCompressor extends HealthProtectCommand {
	private final Compressor compressor;
	
	public InterruptCompressor(Compressor compressor) {
		this.compressor = compressor;
	}
	
	protected void initialize() {
		compressor.stop();
	}
	
	public void reset() {
		compressor.start();
	}
	
	protected void execute() {}
	
	protected boolean isFinished() {
		return true; // Once we stop the compressor, we are done
	}
	
	protected void end() {}
	
	protected void interrupted() {}
}
