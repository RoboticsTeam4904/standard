package org.usfirst.frc4904.standard.commands.systemchecks;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.networktables.NetworkTableEntry;

/**
 * A check on the persistence of Network Tables
 */
public class NetworkTablesCheck extends BaseCheck {
	protected static final String SAVE_FILE = "/home/lvuser/logs/networktables/testTable.log";
	protected static final String CHECK_NAME = "NetworkTables";
	protected static final double DEFAULT_ENTRY = 4.904;
	protected static NetworkTableInstance inst;
	protected static NetworkTable table;
	protected static NetworkTableEntry entry;
	protected static String[] entries;

	/**
	 * @param timeout
	 *                duration of check
	 */
	public NetworkTablesCheck(double timeout) {
		super("NetworkTableCheck", timeout, CHECK_NAME);
	}

	public NetworkTablesCheck() {
		this(DEFAULT_TIMEOUT);
	}

	/**
	 * Constructs network tables and tests the persistence of Network table data
	 */
	public void initialize() {
		inst = NetworkTableInstance.getDefault();
		table = inst.getTable("table");
		entry = table.getEntry("entry");
		entry.setDefaultDouble(DEFAULT_ENTRY);
		try {
			table.saveEntries(SAVE_FILE);
			entries = table.loadEntries(SAVE_FILE);
		}
		catch (PersistentException e) {
			updateStatusFail(CHECK_NAME, e);
		}
		if (entries[0] != Double.toString(DEFAULT_ENTRY)) { // TODO: Check formatting of the entries array
			updateStatusFail(CHECK_NAME, new Exception("LOADED ARRAY NOT EQUAL TO SAVED ARRAY"));
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}