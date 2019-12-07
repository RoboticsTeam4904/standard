package org.usfirst.frc4904.standard;

import java.io.BufferedOutputStream;
import org.usfirst.frc4904.standard.LogKitten;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Kitten {
	protected final static String LOG_PATH = LogKitten.getLogPath();
	public String category;
	public BufferedOutputStream output;
	public String generalPath;
	public File pathDirectory;
	public String specificPath;
	public String specificAliasPath;
	public File logger;
	public File loggerAlias;
	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	protected volatile boolean mute;
	
	public Kitten(String category) {
		this.category = category;
		this.generalPath = Kitten.LOG_PATH + this.category;
		this.pathDirectory = new File(this.generalPath);
		this.specificPath = this.generalPath + timestamp() + ".log";
		this.logger = new File(this.specificPath);
		this.specificAliasPath = this.specificPath + "recent.log";
		this.loggerAlias = new File(this.specificAliasPath);
		this.mute = false;
	}
	
	public boolean getMute() {
		return this.getMute();
	}
	
	public void setMute(boolean mute) {
		this.mute = mute;
	}
	
	public synchronized String timestamp() {
		return TIMESTAMP_FORMAT.format(new Date());
	}
	

}
