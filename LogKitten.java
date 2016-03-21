package org.usfirst.frc4904.standard;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Comparator;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.communication.HALControlWord;

public class LogKitten {
	private static FileOutputStream fileOutput;
	public final static KittenLevel LEVEL_WTF = new KittenLevel("WTF", -1);
	public final static KittenLevel LEVEL_FATAL = new KittenLevel("FATAL", 0);
	public final static KittenLevel LEVEL_ERROR = new KittenLevel("ERROR", 1);
	public final static KittenLevel LEVEL_WARN = new KittenLevel("WARN", 2);
	public final static KittenLevel LEVEL_VERBOSE = new KittenLevel("VERBOSE", 3);
	public final static KittenLevel LEVEL_DEBUG = new KittenLevel("DEBUG", 4);
	public static KittenLevel DEFAULT_LOG_LEVEL = LogKitten.LEVEL_VERBOSE;
	public static KittenLevel DEFAULT_PRINT_LEVEL = LogKitten.LEVEL_WARN;
	public static KittenLevel DEFAULT_DS_LEVEL = LogKitten.DEFAULT_PRINT_LEVEL;
	private static KittenLevel logLevel = LogKitten.DEFAULT_LOG_LEVEL;
	private static KittenLevel printLevel = LogKitten.DEFAULT_PRINT_LEVEL;
	private static KittenLevel dsLevel = LogKitten.DEFAULT_DS_LEVEL;
	private static String LOG_PATH = "/home/lvuser/logs/";
	private static String LOG_ALIAS_PATH = LogKitten.LOG_PATH + "recent.log";
	private static volatile boolean PRINT_MUTE = false;
	static {
		File logPathDirectory = new File(LogKitten.LOG_PATH);
		try {
			if (!logPathDirectory.isDirectory()) { // ensure that the directory /home/lvuser/logs/ exists
				logPathDirectory.mkdirs(); // otherwise create all the directories of the path
			}
		}
		catch (SecurityException se) {
			System.out.println("Could not create log directory");
			se.printStackTrace();
		}
		String filePath = LogKitten.LOG_PATH + LogKitten.timestamp() + ".log"; // Set this sessions log to /home/lvuser/logs/[current time].log
		File file = new File(filePath);
		try {
			// Create new file if it doesn't exist (this should happen)
			file.createNewFile(); // creates if does not exist
			// Create FileOutputStream to actually write to the file.
			LogKitten.fileOutput = new FileOutputStream(file);
		}
		catch (IOException ioe) {
			System.out.println("Could not open logfile");
			ioe.printStackTrace();
		}
		File logAlias = new File(LogKitten.LOG_ALIAS_PATH);
		try {
			if (logAlias.exists()) {
				logAlias.delete();
			}
			Files.createSymbolicLink(logAlias.toPath(), file.toPath());
		}
		catch (IOException ioe) {
			System.out.println("Could not alias logfile");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Get the name of a logger method's caller
	 *
	 * @return the caller for the callee `f`, `e`, `w`, `v`, or `d`
	 */
	private static String getLoggerMethodCallerMethodName() {
		return Thread.currentThread().getStackTrace()[4].getMethodName(); // caller of the logger method is fifth in the stack trace
	}
	
	/**
	 * Get the name of a logger method's calling class
	 *
	 * @return the caller for the callee `f`, `e`, `w`, `v`, or `d`
	 */
	private static String getLoggerMethodCallerClassName() {
		String[] trace = Thread.currentThread().getStackTrace()[4].getClassName().split("\\."); // caller of the logger method is fifth in the stack trace
		if (trace.length == 0) {
			return "";
		}
		return trace[trace.length - 1]; // don't include the package name
	}
	
	/**
	 * Set the default level for which logs will be streamed to a file (for all LogKitten instances)
	 *
	 * @param DEFAULT_LOG_LEVEL
	 *        default write-to-file level
	 */
	public static void setDefaultLogLevel(KittenLevel DEFAULT_LOG_LEVEL) {
		LogKitten.logLevel = DEFAULT_LOG_LEVEL;
	}
	
	/**
	 * Set the default level for which logs will be printed to the console (for all LogKitten instances)
	 *
	 * @param DEFAULT_PRINT_LEVEL
	 *        default console log level
	 */
	public static void setDefaultPrintLevel(KittenLevel DEFAULT_PRINT_LEVEL) {
		LogKitten.printLevel = DEFAULT_PRINT_LEVEL;
	}
	
	/**
	 * Set the default level for which logs will be printed to the driver station (for all LogKitten instances)
	 *
	 * @param DEFAULT_DS_LEVEL
	 *        default driver station level
	 */
	public static void setDefaultDSLevel(KittenLevel DEFAULT_DS_LEVEL) {
		LogKitten.dsLevel = DEFAULT_DS_LEVEL;
	}
	
	/**
	 * Set the logfile path for all LogKitten instances
	 *
	 * @param LOG_PATH
	 *        logfile path as a string
	 */
	public static void setLogPath(String LOG_PATH) {
		LogKitten.LOG_PATH = LOG_PATH;
	}
	
	/**
	 * Mutes all messages except those overriding
	 * (useful for debugging)
	 *
	 * @param mute
	 */
	public static void setPrintMute(boolean mute) {
		LogKitten.PRINT_MUTE = mute;
	}
	
	/**
	 * Like DriverStation.reportError, but w/o stack trace nor printing to System.err
	 *
	 * @see edu.wpi.first.wpilibj.DriverStation.reportError
	 * @param errorString
	 */
	private static void reportErrorToDriverStation(String errorString) {
		HALControlWord controlWord = FRCNetworkCommunicationsLibrary.HALGetControlWord();
		if (controlWord.getDSAttached()) {
			FRCNetworkCommunicationsLibrary.HALSetErrorData(errorString);
		}
	}
	
	private static synchronized void logMessage(String message, KittenLevel level, boolean override) {
		if (LogKitten.logLevel.compareTo(level) >= 0) {
			String content = LogKitten.timestamp() + " " + level.getName() + ": " + LogKitten.getLoggerMethodCallerMethodName() + ": " + message + " \n";
			try {
				if (LogKitten.fileOutput != null) {
					LogKitten.fileOutput.write(content.getBytes());
					LogKitten.fileOutput.flush();
				} else {
					System.out.println("Error logging: logfile not open");
				}
			}
			catch (IOException ioe) {
				System.out.println("Error logging " + level.getName() + " message");
				ioe.printStackTrace();
			}
		}
		if (!LogKitten.PRINT_MUTE || override) {
			String printContent = level.getName() + ": " + LogKitten.getLoggerMethodCallerClassName() + "#" + LogKitten.getLoggerMethodCallerMethodName() + ": " + message + " \n";
			if (LogKitten.printLevel.compareTo(level) >= 0) {
				System.out.println(printContent);
			}
			if (LogKitten.dsLevel.compareTo(level) >= 0) {
				LogKitten.reportErrorToDriverStation(printContent);
			}
		}
	}
	
	/**
	 * What a Terrible Failure: Report a condition that should never happen, allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void wtf(String message, boolean override) {
		LogKitten.logMessage(message, LogKitten.LEVEL_WTF, override);
	}
	
	/**
	 * What a Terrible Failure: Report a condition that should never happen
	 *
	 * @param message
	 *        the message to log
	 */
	public static void wtf(String message) { // Log WTF message
		LogKitten.logMessage(message, LogKitten.LEVEL_WTF, false);
	}
	
	/**
	 * Log message at level LEVEL_FATAL allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void f(String message, boolean override) {
		LogKitten.logMessage(message, LogKitten.LEVEL_FATAL, override);
	}
	
	/**
	 * Log message at level LEVEL_FATAL
	 *
	 * @param message
	 *        the message to log
	 */
	public static void f(String message) { // Log fatal message
		LogKitten.logMessage(message, LogKitten.LEVEL_FATAL, false);
	}
	
	/**
	 * Log message at LEVEL_ERROR allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void e(String message, boolean override) {
		LogKitten.logMessage(message, LogKitten.LEVEL_ERROR, override);
	}
	
	/**
	 * Log message at level LEVEL_ERROR
	 *
	 * @param message
	 *        the message to log
	 */
	public static void e(String message) { // Log error message
		LogKitten.logMessage(message, LogKitten.LEVEL_ERROR, false);
	}
	
	/**
	 * Log message at LEVEL_WARN allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void w(String message, boolean override) {
		LogKitten.logMessage(message, LogKitten.LEVEL_WARN, override);
	}
	
	/**
	 * Log message at level LEVEL_WARN
	 *
	 * @param message
	 *        the message to log
	 */
	public static void w(String message) { // Log warn message
		LogKitten.logMessage(message, LogKitten.LEVEL_WARN, false);
	}
	
	/**
	 * Log message at LEVEL_VERBOSE allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void v(String message, boolean override) {
		LogKitten.logMessage(message, LogKitten.LEVEL_VERBOSE, override);
	}
	
	/**
	 * Log message at level LEVEL_VERBOSE
	 *
	 * @param message
	 *        the message to log
	 */
	public static void v(String message) { // Log verbose message
		LogKitten.logMessage(message, LogKitten.LEVEL_VERBOSE, false);
	}
	
	/**
	 * Log message at LEVEL_VERBOSE (INFO links to verbose) allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void i(String message, boolean override) {
		LogKitten.logMessage(message, LogKitten.LEVEL_VERBOSE, override);
	}
	
	/**
	 * Log message at LEVEL_VERBOSE (INFO links to verbose)
	 *
	 * @param message
	 */
	public static void i(String message) {
		LogKitten.logMessage(message, LogKitten.LEVEL_VERBOSE, false);
	}
	
	/**
	 * Log message at level LEVEL_DEBUG allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void d(String message, boolean override) {
		LogKitten.logMessage(message, LogKitten.LEVEL_DEBUG, override);
	}
	
	/**
	 * Log message at level LEVEL_DEBUG
	 *
	 * @param message
	 *        the message to log
	 */
	public static void d(String message) { // Log debug message
		LogKitten.logMessage(message, LogKitten.LEVEL_DEBUG, false);
	}
	
	/**
	 * Log exception at level LEVEL_ERROR allowing override
	 *
	 * @param ex
	 *        the exception to log
	 * @param override
	 *        whether or not to override
	 */
	public static void ex(Exception ex, boolean override) {
		String exceptionString = ex.toString() + "\n";
		StringBuffer stackTraceString = new StringBuffer();
		for (StackTraceElement element : ex.getStackTrace()) {
			stackTraceString.append(element.toString() + "\n");
		}
		LogKitten.logMessage(exceptionString + stackTraceString.toString(), LogKitten.LEVEL_ERROR, override);
	}
	
	/**
	 * Log exception at level LEVEL_ERROR
	 *
	 * @param ex
	 *        the exception to log
	 */
	public static void ex(Exception ex) {
		LogKitten.ex(ex, false);
	}
	
	/**
	 * Tries to close the logfile stream
	 */
	public static synchronized void clean() {
		try {
			if (LogKitten.fileOutput != null) {
				LogKitten.fileOutput.close();
			}
		}
		catch (IOException ioe) {
			System.out.println("Could not close logfile output. This should never happen");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Get a timestamp for the current datetime - me-wow!
	 *
	 * @return timestamp as string in the format "YEAR-MONTH-DAY_HOUR:MIN:SEC"
	 */
	private static String timestamp() {
		Calendar now = Calendar.getInstance();
		String timestamp = Integer.toString(now.get(Calendar.YEAR));
		timestamp += "-" + Integer.toString(now.get(Calendar.MONTH) + 1);
		timestamp += "-" + Integer.toString(now.get(Calendar.DATE));
		timestamp += "_" + Integer.toString(now.get(Calendar.HOUR_OF_DAY));
		timestamp += ":" + Integer.toString(now.get(Calendar.MINUTE));
		timestamp += ":" + Integer.toString(now.get(Calendar.SECOND));
		return timestamp;
	}
	
	private static class KittenLevel implements Comparable<KittenLevel>, Comparator<KittenLevel> {
		private final String name;
		private final int severity;
		
		/**
		 * Construct a new KittenLevel instance
		 *
		 * @param name
		 * @param severity
		 */
		public KittenLevel(String name, int severity) {
			this.severity = severity;
			this.name = name;
		}
		
		/**
		 * Get the level severity
		 *
		 * @return the level severity as an int
		 */
		public int getSeverity() {
			return severity;
		}
		
		/**
		 * Get the level name
		 *
		 * @return level name as a string
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Compare the severity of two KittenLevels
		 */
		@Override
		public int compare(KittenLevel o1, KittenLevel o2) {
			return o1.getSeverity() - o2.getSeverity();
		}
		
		/**
		 * Compare the instance's severity to another KittenLevel
		 */
		@Override
		public int compareTo(KittenLevel o) {
			if (compare(this, o) > 0) {
				return 1;
			} else if (compare(this, o) == 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}
