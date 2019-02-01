package org.usfirst.frc4904.standard.commands.systemchecks;


import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.wpilibj.Compressor;

public class CompressorCheck extends SystemCheck {
    protected final Compressor[] compressors;

    public CompressorCheck(String name, double timeout, Compressor... compressors) {
        super("CompressorCheck", timeout, compressors);
        this.compressors = compressors;
    }

    public CompressorCheck(String name, Compressor... compressors) {
        this(name, DEFAULT_TIMEOUT, compressors);
    }

    public CompressorCheck(double timeout, Compressor... compressors) {
        this("CompressorCheck", timeout, compressors);
    }

    public CompressorCheck(Compressor... compressors) {
        this("CompressorCheck", compressors);
    }

    public void execute() {
        for (Compressor compressor : compressors) {
            if (compressor.getCompressorNotConnectedFault() || compressor.getCompressorNotConnectedStickyFault()
                || !compressor.enabled()) {
                updateStatus(compressor.getName(), SystemStatus.FAIL, new Exception("COMPRESSOR NOT CONNECTED"));
            } else if (compressor.getCompressorShortedFault() || compressor.getCompressorShortedStickyFault()) {
                updateStatus(compressor.getName(), SystemStatus.FAIL, new Exception("COMPRESSOR SHORTED"));
            } else if (compressor.getCompressorCurrentTooHighFault() || compressor.getCompressorCurrentTooHighStickyFault()) {
                updateStatus(compressor.getName(), SystemStatus.FAIL, new Exception("CURRENT TOO HIGH"));
            } else if (compressor.getPressureSwitchValue()) { // TODO: Check if this works as intended
                updateStatus(compressor.getName(), SystemStatus.FAIL, new Exception("LOW PRESSURE"));
            }
        }
    }
}