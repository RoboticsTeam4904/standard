package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.wpilibj.Compressor;

public class CompressorCheck extends SystemCheck {
    protected final Compressor[] compressors;
    
    public CompressorCheck(String name, Compressor... compressors) {
        super("CompressorCheck", compressors);
        this.compressors = compressors;
    }

    public void execute() {
        for (Compressor compressor : compressors) {
            if (compressor.getCompressorNotConnectedFault() || compressor.getCompressorNotConnectedStickyFault() || !compressor.enabled()){
                updateStatus(compressor.getName(), SystemStatus.FAIL, "COMPRESSOR NOT CONNECTED");

            }
            else if (compressor.getCompressorShortedFault() || compressor.getCompressorShortedStickyFault()) {
                updateStatus(compressor.getName(), SystemStatus.FAIL, "COMPRESSOR SHORTED");
            }

            else if (compressor.getCompressorCurrentTooHighFault() || compressor.getCompressorCurrentTooHighStickyFault()) {
                updateStatus(compressor.getName(), SystemStatus.FAIL, "CURRENT TOO HIGH");
            }
            else if (compressor.getPressureSwitchValue()){ //TODO: Check if this works as intended
                updateStatus(compressor.getName(), SystemStatus.FAIL, "LOW PRESSURE");
            }


            
        }
    }

} 