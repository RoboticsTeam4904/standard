package org.usfirst.frc4904.standard.custom.sensors;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.usfirst.frc4904.standard.custom.CustomCAN;

public class CANSensor extends CustomCAN {
	
	private int[] cachedValues;

	public CANSensor(String name, int id, int modes) {
		super(name, id);
		cachedValues = new int[modes];
		for(int i = 0; i < modes; i++){
			cachedValues[i] = 0; // Should we make this a more obscure value (e.g. 2^32 - 1)?
		}
	}
	
	/**
	 * Mode determines what signal from the
	 * CAN node to look for. The first int
	 * is 0 if the data was returned
	 * correctly and -1 if no data was returned
	 * @param mode
	 * @return
	 */
	public int read(int mode, int retryMax){
		for(int i = 0; i < retryMax; i++){
			ByteBuffer rawData = super.readBuffer();
			int msgMode = rawData.getInt(0);
			int value = rawData.getInt(1);
			cachedValues[msgMode] = value;
			if(msgMode == mode){
				return value;
			}
		}
		return cachedValues[mode];
	}
	
	public int read(int mode){
		return read(mode, 10);
	}

}
