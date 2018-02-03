package org.usfirst.frc4904.standard;

import java.io.BufferedOutputStream;
import org.usfirst.frc4904.standard.LogKitten;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class Kitten {
	protected final static String LOG_PATH = LogKitten.LOG_PATH;
	public String category;
	public BufferedOutputStream ouput;
	public String generalPath;
	public File pathDirectory;
	public String specificPath;
	public String specificAliasPath;
	public File logger;
	public File loggerAlias;
	protected Map<String, Boolean> options;
	protected volatile boolean mute;
	
	public Kitten(String category, ) {
		this.category = category
		this.generalPath = Kitten.LOG_PATH + this.category;
		this.pathDirectory = new File(this.generalPath);
		this.specificPath = this.generalPath + LogKitten.timestamp + ".log";
		this.logger = new File(this.specificPath);
		this.specificAliasPath = this.specificPath + "recent.log";
		this.loggerAlias = new File(this.specificAliasPath);
		this.options = new HashMap<String, Boolean>();
		this.mute = false;
	}
	
	public boolean getMute() {
		return this.getMute();
	}
	
	public void setMute(boolean mute) {
		this.mute = mute;
	}
	public void config(Boolean[] options) {
		if(options[0] != null) {
			this.options.put("logErrors", options[0]);
		} else {
			this.options.put("logErrors", false);
		}
		if(options[0] != null) {
			this.options.put("logRobotMode", options[1]);
		} else {
			this.options.put("logRobotMode", false);
		}
		if(options[0] != null) {
			this.options.put("logKittenLevel", options[2]);
		} else {
			this.options.put("logKittenLevel", false);
		}
		if(options[0] != null) {
			this.options.put("logTimestamp", options[3]);
		} else {
			this.options.put("logTimestamp", false);
		}
		if(options[0] != null) {
			this.options.put("logCallingClass", options[4]);
		} else {
			this.options.put("logCallingClass", false);
		}
		if(options[0] != null) {
			this.options.put("logCallingMethod", options[5]);
		} else {
			this.options.put("logCallingMethod", false);
		}
		if(options[0] != null) {
			this.options.put("logMessage", options[6]);
		} else {
			this.options.put("logMessage", false);
		}
	}
}
