package org.usfirst.frc4904.standard;

import java.io.BufferedOutputStream;
import org.usfirst.frc4904.standard.LogKitten;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

public class Kitten {
	protected final static String LOG_PATH = LogKitten.getLogPath();
	public String category;
	public BufferedOutputStream ouput;
	public String generalPath;
	public File pathDirectory;
	public String specificPath;
	public String specificAliasPath;
	public File logger;
	public File loggerAlias;
	protected Map<String, Boolean> options;
	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	protected volatile boolean mute;
	
	public Kitten(String category, Boolean[] options) {
		this.category = category;
		this.generalPath = Kitten.LOG_PATH + this.category;
		this.pathDirectory = new File(this.generalPath);
		this.specificPath = this.generalPath + timestamp() + ".log";
		this.logger = new File(this.specificPath);
		this.specificAliasPath = this.specificPath + "recent.log";
		this.loggerAlias = new File(this.specificAliasPath);
		this.options = new HashMap<String, Boolean>();
		this.config(options);
		this.mute = false;
	}
	
	public Kitten(String category) {
		this.category = category;
		this.generalPath = Kitten.LOG_PATH + this.category;
		this.pathDirectory = new File(this.generalPath);
		this.specificPath = this.generalPath + timestamp() + ".log";
		this.logger = new File(this.specificPath);
		this.specificAliasPath = this.specificPath + "recent.log";
		this.loggerAlias = new File(this.specificAliasPath);
		this.options = new HashMap<String, Boolean>();
		this.config(true);
		this.mute = false;
	}
	
	public boolean getMute() {
		return this.getMute();
	}
	
	public void setMute(boolean mute) {
		this.mute = mute;
	}
	
	private static synchronized String timestamp() {
		return TIMESTAMP_FORMAT.format(new Date());
	}
	
	public void config(Boolean option) {
		this.options.put("logErrors", option);
		this.options.put("logRobotMode", option);
		this.options.put("logKittenLevel", option);
		this.options.put("logTimestamp", option);
		this.options.put("logCallingClass", option);
		this.options.put("logCallingMethod", option);
		this.options.put("logMessage", option);
	}
	
	public void config(Boolean[] options) {
		if(options[0] != null) {
			this.options.put("logErrors", options[0]);
		} else {
			this.options.put("logErrors", false);
		}
		if(options[1] != null) {
			this.options.put("logRobotMode", options[1]);
		} else {
			this.options.put("logRobotMode", false);
		}
		if(options[3] != null) {
			this.options.put("logKittenLevel", options[3]);
		} else {
			this.options.put("logKittenLevel", false);
		}
		if(options[4] != null) {
			this.options.put("logTimestamp", options[4]);
		} else {
			this.options.put("logTimestamp", false);
		}
		if(options[5] != null) {
			this.options.put("logCallingClass", options[5]);
		} else {
			this.options.put("logCallingClass", false);
		}
		if(options[6] != null) {
			this.options.put("logCallingMethod", options[6]);
		} else {
			this.options.put("logCallingMethod", false);
		}
		if(options[7] != null) {
			this.options.put("logMessage", options[7]);
		} else {
			this.options.put("logMessage", false);
		}
	}
}
