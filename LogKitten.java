package org.usfirst.frc4904.standard;


import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.hal.HAL;

public class LogKitten {
	private static BufferedOutputStream globalOutput;
	private static BufferedOutputStream autonOutput;
	private static BufferedOutputStream teleopOutput;
	private static BufferedOutputStream unknownOutput;
	public final static KittenLevel LEVEL_WTF = KittenLevel.WTF;
	public final static KittenLevel LEVEL_FATAL = KittenLevel.FATAL;
	public final static KittenLevel LEVEL_ERROR = KittenLevel.ERROR;
	public final static KittenLevel LEVEL_WARN = KittenLevel.WARN;
	public final static KittenLevel LEVEL_VERBOSE = KittenLevel.VERBOSE;
	public final static KittenLevel LEVEL_DEBUG = KittenLevel.DEBUG;
	public final static KittenLevel DEFAULT_LOG_LEVEL = KittenLevel.DEBUG;
	public final static KittenLevel DEFAULT_PRINT_LEVEL = KittenLevel.WARN;
	public final static KittenLevel DEFAULT_DS_LEVEL = LogKitten.DEFAULT_PRINT_LEVEL;
	private static KittenLevel logLevel = LogKitten.DEFAULT_LOG_LEVEL;
	private static KittenLevel printLevel = LogKitten.DEFAULT_PRINT_LEVEL;
	private static KittenLevel dsLevel = LogKitten.DEFAULT_DS_LEVEL;
	private static String GLOBAL_PATH = "/home/lvuser/logs/global/";
	private static String LOG_PATH = "/home/lvuser/logs/";
	private static String AUTON_PATH = "/home/lvuser/logs/auton/";
	private static String TELEOP_PATH = "/home/lvuser/logs/teleop/";
	private static String UNKNOWN_PATH = "/home/lvuser/logs/unknown/";
	private static String GLOBAL_ALIAS_PATH = LogKitten.GLOBAL_PATH + "recent.log";
	private static String LOG_ALIAS_PATH = Logkitten.LOG_PATH + "recent.log";
	private static String AUTON_ALIAS_PATH = Logkitten.AUTON_PATH + "recent.log";
	private static String TELEOP_ALIAS_PATH = Logkitten.TELEOP_PATH + "recent.log";
	private static String UNKNOWN_ALIAS_PATH = Logkitten.UNKNOWN_PATH + "recent.log";
	private static volatile boolean PRINT_MUTE = false;
	private static volatile boolean AUTON_MUTE = false;
	private static volatile boolean TELEOP_MUTE = false;
	private static volatile boolean UNKNOWN_MUTE = false;
	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	static {
		File globalPathDirectory = new File(LogKitten.GLOBAL_PATH);
		File autonPathDirectory = new File(LogKitten.AUTON_PATH);
		File teleopPathDirectory = new File(LogKitten.TELEOP_PATH);
		File unknownPathDirectory = new File(LogKitten.UNKNOWN_PATH);
		try {
			if (!globalPathDirectory.isDirectory()) { // ensure that the directory /home/lvuser/logs/global/ exists
				globalPathDirectory.mkdirs(); // otherwise create all the directories of the path
			}
		}
		catch (SecurityException se) {
			System.out.println("Could not create global log directory");
			se.printStackTrace();
		}
		try {
			if (!autonPathDirectory.isDirectory()) { // ensure that the directory /home/lvuser/logs/global/ exists
				autonPathDirectory.mkdirs(); // otherwise create all the directories of the path
			}
		}
		catch (SecurityException se) {
			System.out.println("Could not create auton log directory");
			se.printStackTrace();
		}
		try {
			if (!teleopPathDirectory.isDirectory()) { // ensure that the directory /home/lvuser/logs/global/ exists
				teleopPathDirectory.mkdirs(); // otherwise create all the directories of the path
			}
		}
		catch (SecurityException se) {
			System.out.println("Could not create teleop log directory");
			se.printStackTrace();
		}
		try {
			if (!unknownPathDirectory.isDirectory()) { // ensure that the directory /home/lvuser/logs/global/ exists
				unknownPathDirectory.mkdirs(); // otherwise create all the directories of the path
			}
		}
		catch (SecurityException se) {
			System.out.println("Could not create unknown log directory");
			se.printStackTrace();
		}
		String globalPath = LogKitten.GLOBAL_PATH + LogKitten.timestamp() + ".log"; // Set this sessions log to /home/lvuser/logs/global/[current time].log
		String autonPath = LogKitten.AUTON_PATH + LogKitten.timestamp() + ".log"; // Set this sessions log to /home/lvuser/logs/auton/[current time].log
		String teleopPath = LogKitten.TELEOP_PATH + LogKitten.timestamp() + ".log"; // Set this sessions log to /home/lvuser/logs/teleop/[current time].log
		String unknownPath = LogKitten.UNKNOWN_PATH + LogKitten.timestamp() + ".log"; // Set this sessions log to /home/lvuser/logs/unknown/[current time].log
		File global = new File(globalPath);
		File auton = new File(autonPath);
		File teleop = new File(teleopPath);
		File unknown = new File(unknownPath);
		try {
			// Create new file if it doesn't exist (this should happen)
			global.createNewFile(); // creates if does not exist
			// Create FileOutputStream to actually write to the file.
			LogKitten.globalOutput = new BufferedOutputStream(new FileOutputStream(global));
		}
		catch (IOException ioe) {
			System.out.println("Could not open global logfile");
			ioe.printStackTrace();
		}
		try {
			// Create new file if it doesn't exist (this should happen)
			auton.createNewFile(); // creates if does not exist
			// Create FileOutputStream to actually write to the file.
			LogKitten.autonOutput = new BufferedOutputStream(new FileOutputStream(auton));
		}
		catch (IOException ioe) {
			System.out.println("Could not open auton logfile");
			ioe.printStackTrace();
		}
		try {
			// Create new file if it doesn't exist (this should happen)
			teleop.createNewFile(); // creates if does not exist
			// Create FileOutputStream to actually write to the file.
			LogKitten.teleopOutput = new BufferedOutputStream(new FileOutputStream(teleop));
		}
		catch (IOException ioe) {
			System.out.println("Could not open teleop logfile");
			ioe.printStackTrace();
		}
		try {
			// Create new file if it doesn't exist (this should happen)
			unknown.createNewFile(); // creates if does not exist
			// Create FileOutputStream to actually write to the file.
			LogKitten.unknownOutput = new BufferedOutputStream(new FileOutputStream(unknown));
		}
		catch (IOException ioe) {
			System.out.println("Could not open unknown logfile");
			ioe.printStackTrace();
		}
		File globalAlias = new File(LogKitten.GLOBAL_ALIAS_PATH);
		File autonAlias = new File(LogKitten.AUTON_ALIAS_PATH);
		File teleopAlias = new File(LogKitten.TELEOP_ALIAS_PATH);
		File unknownAlias = new File(LogKitten.UNKNOWN_ALIAS_PATH);
		try {
			if (globalAlias.exists()) {
				globalAlias.delete();
			}
			Files.createSymbolicLink(globalAlias.toPath(), global.toPath());
		}
		catch (IOException ioe) {
			System.out.println("Could not alias global logfile");
			ioe.printStackTrace();
		}
		try {
			if (autonAlias.exists()) {
				autonAlias.delete();
			}
			Files.createSymbolicLink(autonAlias.toPath(), auton.toPath());
		}
		catch (IOException ioe) {
			System.out.println("Could not alias auton logfile");
			ioe.printStackTrace();
		}
		try {
			if (teleopAlias.exists()) {
				teleopAlias.delete();
			}
			Files.createSymbolicLink(teleopAlias.toPath(), teleop.toPath());
		}
		catch (IOException ioe) {
			System.out.println("Could not alias teleop logfile");
			ioe.printStackTrace();
		}
		try {
			if (unknownAlias.exists()) {
				unknownAlias.delete();
			}
			Files.createSymbolicLink(unknownAlias.toPath(), unknown.toPath());
		}
		catch (IOException ioe) {
			System.out.println("Could not alias unknown logfile");
			ioe.printStackTrace();
		}
	}

	/**
	 * Gets the mode of the robot
	 * 
	 * @return a string with either auton or teleop
	 */
	private String getRobotMode(){
		String robotMode = null;
		if(DriverStation.getInstance().isAutonomous()) {
			robotMode = "AUTON";
		}else if(!DriverStation.getInstance().isAutonomous()) {
			robotMode = "TELEOP";
		} else {
			robotMode = "UNKNOWN";
		}
		return robotMode;
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
	 * Set the globalfile path for all LogKitten instances
	 *
	 * @param GLOBAL_PATH
	 *        globalfile path as a string
	 */
	public static void setGlobalPath(String GLOBAL_PATH) {
		LogKitten.GLOBAL_PATH = GLOBAL_PATH;
	}

	/**
	 * Set the autonfile path for all LogKitten instances
	 *
	 * @param AUTON_PATH
	 *        globalfile path as a string
	 */
	public static void setAutonPath(String AUTON_PATH) {
		LogKitten.AUTON_PATH = AUTON_PATH;
	}

	/**
	 * Set the teleopfile path for all LogKitten instances
	 *
	 * @param TELEOP_PATH
	 *        teleopfile path as a string
	 */
	public static void setTeleopPath(String TELEOP_PATH) {
		LogKitten.TELEOP_PATH = TELEOP_PATH;
	}
	
	/**
	 * Set the unknownfile path for all LogKitten instances
	 *
	 * @param UNKNOWN_PATH
	 *        unknownfile path as a string
	 */
	public static void setUnknownPath(String UNKNOWN_PATH) {
		LogKitten.UNKNOWN_PATH = UNKNOWN_PATH;
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
	 * Mutes all logging to the auton file
	 * (useful for debugging)
	 *
	 * @param mute
	 */
	public static void setAutonMute(boolean mute) {
		LogKitten.Auton_MUTE = mute;
	}
	
	/**
	 * Mutes all logging to the teleop file
	 * (useful for debugging)
	 *
	 * @param mute
	 */
	public static void setTeleopMute(boolean mute) {
		LogKitten.TELEOP_MUTE = mute;
	}
	
	/**
	 * Mutes all logging to the unknown file
	 * (useful for debugging)
	 *
	 * @param mute
	 */
	public static void setUnknownMute(boolean mute) {
		LogKitten.UNKNOWN_MUTE = mute;
	}
	
	/**
	 * Like DriverStation.reportError, but without stack trace nor printing to System.err
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
			String content = LogKitten.getRobotMode() + " " + LogKitten.timestamp() + " " + level.getName() + ": " + LogKitten.getLoggerMethodCallerMethodName()
				+ ": " + message + " \n";
			try {
				if (LogKitten.globalOutput != null) {
					LogKitten.globalOutput.write(content.getBytes());
				} else {
					System.out.println("Error logging: global logfile not open");
				}
			}
			catch (IOException ioe) {
				System.out.println("Error logging " + level.getName() + " message");
				ioe.printStackTrace();
			}
		}
		if (!LogKitten.AUTON_MUTE && (LogKitten.getRobotMode() == "AUTON")) {
			String content = LogKitten.getRobotMode() + " " + LogKitten.timestamp() + " " + level.getName() + ": " + Logkitten.getLoggerMethodName()
				+ ": " + message + " \n";
			try {
				if (LogKitten.autonOutput != null) {
					LogKitten.autonOutput.write(content.getBytes());
				} else {
					System.out.println("Error logging: auton logfile not open");
				}
			}
		}
		if (!LogKitten.TELEOP_MUTE && (LogKitten.getRobotMode() == "TELEOP")) {
			String content = LogKitten.getRobotMode() + " " + LogKitten.timestamp() + " " + level.getName() + ": " + Logkitten.getLoggerMethodName()
				+ ": " + message + " \n";
			try {
				if (LogKitten.teleopOutput != null) {
					LogKitten.teleopOutput.write(content.getBytes());
				} else {
					System.out.println("Error logging: teleop logfile not open");
				}
			}
		}
		if (!LogKitten.UNKNOWN_MUTE && (LogKitten.getRobotMode() == "UNKNOWN")) {
			String content = LogKitten.getRobotMode() + " " + LogKitten.timestamp() + " " + level.getName() + ": " + Logkitten.getLoggerMethodName()
				+ ": " + message + " \n";
			try {
				if (LogKitten.unknownOutput != null) {
					LogKitten.unknownOutput.write(content.getBytes());
				} else {
					System.out.println("Error logging: unknown logfile not open");
				}
			}
		}
		if (!LogKitten.PRINT_MUTE || override) {
			String printContent = level.getName() + ": " + LogKitten.getRobotMode() + " " + LogKitten.getLoggerMethodCallerClassName() + "#"
				+ LogKitten.getLoggerMethodCallerMethodName() + ": " + message + " \n";
			if (LogKitten.printLevel.compareTo(level) >= 0) {
				System.out.println(printContent);
			}
			if (LogKitten.dsLevel.compareTo(level) >= 0) {
				LogKitten.reportErrorToDriverStation(
					LogKitten.getRobotMode() + " " + LogKitten.getLoggerMethodCallerClassName() + "#" + LogKitten.getLoggerMethodCallerMethodName(),
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
		StringWriter stackTraceString = new StringWriter();
		ex.printStackTrace(new PrintWriter(stackTraceString));
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
