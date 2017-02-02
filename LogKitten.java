package org.usfirst.frc4904.standard;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.wpi.first.wpilibj.hal.HAL;

public class LogKitten {
	private static FileOutputStream fileOutput;
	public final static KittenLevel LEVEL_WTF = KittenLevel.WTF;
	public final static KittenLevel LEVEL_FATAL = KittenLevel.FATAL;
	public final static KittenLevel LEVEL_ERROR = KittenLevel.ERROR;
	public final static KittenLevel LEVEL_WARN = KittenLevel.WARN;
	public final static KittenLevel LEVEL_VERBOSE = KittenLevel.VERBOSE;
	public final static KittenLevel LEVEL_DEBUG = KittenLevel.DEBUG;
	public final static KittenLevel DEFAULT_LOG_LEVEL = KittenLevel.VERBOSE;
	public final static KittenLevel DEFAULT_PRINT_LEVEL = KittenLevel.WARN;
	public final static KittenLevel DEFAULT_DS_LEVEL = LogKitten.DEFAULT_PRINT_LEVEL;
	private static KittenLevel logLevel = LogKitten.DEFAULT_LOG_LEVEL;
	private static KittenLevel printLevel = LogKitten.DEFAULT_PRINT_LEVEL;
	private static KittenLevel dsLevel = LogKitten.DEFAULT_DS_LEVEL;
	private static String LOG_PATH = "/home/lvuser/logs/";
	private static String LOG_ALIAS_PATH = LogKitten.LOG_PATH + "recent.log";
	private static volatile boolean PRINT_MUTE = false;
	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
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
	 * (updated for 2017 WPILib release)
	 *
	 * @see edu.wpi.first.wpilibj.DriverStation.reportError
	 * @param errorString
	 */
	private static void reportErrorToDriverStation(String details, String errorMessage, KittenLevel logLevel) {
		HAL.sendError(true, logLevel.getSeverity(), false, errorMessage, details, "", false);
	}

	public static synchronized void logMessage(Object message, KittenLevel level, boolean override) {
		message = message.toString(); // Not strictly needed, but good practice
		if (LogKitten.logLevel.compareTo(level) >= 0) {
			String content = LogKitten.timestamp() + " " + level.getName() + ": " + LogKitten.getLoggerMethodCallerMethodName()
				+ ": " + message + " \n";
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
			String printContent = level.getName() + ": " + LogKitten.getLoggerMethodCallerClassName() + "#"
				+ LogKitten.getLoggerMethodCallerMethodName() + ": " + message + " \n";
			if (LogKitten.printLevel.compareTo(level) >= 0) {
				System.out.println(printContent);
			}
			if (LogKitten.dsLevel.compareTo(level) >= 0) {
				LogKitten.reportErrorToDriverStation(
					LogKitten.getLoggerMethodCallerClassName() + "#" + LogKitten.getLoggerMethodCallerMethodName(),
					level.getName() + ": " + message, level);
			}
		}
	}

	/**
	 * What a Terrible Failure: Report a condition that should never happen, allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void wtf(Object message, boolean override) {
		LogKitten.logMessage(message, KittenLevel.WTF, override);
	}

	/**
	 * What a Terrible Failure: Report a condition that should never happen
	 *
	 * @param message
	 *        the message to log
	 */
	public static void wtf(Object message) { // Log WTF message
		LogKitten.logMessage(message, KittenLevel.WTF, false);
	}

	/**
	 * Log message at level FATAL allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void f(Object message, boolean override) {
		LogKitten.logMessage(message, KittenLevel.FATAL, override);
	}

	/**
	 * Log message at level FATAL
	 *
	 * @param message
	 *        the message to log
	 */
	public static void f(Object message) { // Log fatal message
		LogKitten.logMessage(message, KittenLevel.FATAL, false);
	}

	/**
	 * Log message at ERROR allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void e(Object message, boolean override) {
		LogKitten.logMessage(message, KittenLevel.ERROR, override);
	}

	/**
	 * Log message at level ERROR
	 *
	 * @param message
	 *        the message to log
	 */
	public static void e(Object message) { // Log error message
		LogKitten.logMessage(message, KittenLevel.ERROR, false);
	}

	/**
	 * Log message at WARN allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void w(Object message, boolean override) {
		LogKitten.logMessage(message, KittenLevel.WARN, override);
	}

	/**
	 * Log message at level WARN
	 *
	 * @param message
	 *        the message to log
	 */
	public static void w(Object message) { // Log warn message
		LogKitten.logMessage(message, KittenLevel.WARN, false);
	}

	/**
	 * Log message at VERBOSE allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void v(Object message, boolean override) {
		LogKitten.logMessage(message, KittenLevel.VERBOSE, override);
	}

	/**
	 * Log message at level VERBOSE
	 *
	 * @param message
	 *        the message to log
	 */
	public static void v(Object message) { // Log verbose message
		LogKitten.logMessage(message, KittenLevel.VERBOSE, false);
	}

	/**
	 * Log message at VERBOSE (INFO links to verbose) allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void i(Object message, boolean override) {
		LogKitten.logMessage(message, KittenLevel.VERBOSE, override);
	}

	/**
	 * Log message at VERBOSE (INFO links to verbose)
	 *
	 * @param message
	 */
	public static void i(Object message) {
		LogKitten.logMessage(message, KittenLevel.VERBOSE, false);
	}

	/**
	 * Log message at level DEBUG allowing override
	 *
	 * @param message
	 * @param override
	 */
	public static void d(Object message, boolean override) {
		LogKitten.logMessage(message, KittenLevel.DEBUG, override);
	}

	/**
	 * Log message at level DEBUG
	 *
	 * @param message
	 *        the message to log
	 */
	public static void d(Object message) { // Log debug message
		LogKitten.logMessage(message, KittenLevel.DEBUG, false);
	}

	/**
	 * Log exception at level ERROR allowing override
	 *
	 * @param ex
	 *        the exception to log
	 * @param override
	 *        whether or not to override
	 */
	public static void ex(Exception ex, boolean override) {
		StringBuilder stackTraceString = new StringBuilder();
		stackTraceString.append(ex.toString());
		stackTraceString.append('\n');
		for (StackTraceElement element : ex.getStackTrace()) {
			stackTraceString.append(element.toString());
			stackTraceString.append('\n');
		}
		LogKitten.logMessage(stackTraceString.toString(), KittenLevel.ERROR, override);
	}

	/**
	 * Log exception at level ERROR
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
	private static synchronized String timestamp() {
		return LogKitten.TIMESTAMP_FORMAT.format(new Date());
	}

	public static enum KittenLevel {
		// Defined in decreasing order of severity. Enum.compareTo uses the definition order to compare enum values.
		WTF, FATAL, ERROR, WARN, VERBOSE, DEBUG;
		/**
		 * Get the level severity
		 *
		 * @return the level severity as an int
		 */
		public int getSeverity() {
			// Severity is the same as the ordinal, which increases with the order of the enum values
			return ordinal();
		}

		/**
		 * Get the level name
		 *
		 * @return level name as a string
		 */
		public String getName() {
			return name(); // Enum.name() is the Java builtin to get the name of an enum value
		}
	}
}
