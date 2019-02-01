package org.usfirst.frc4904.standard.commands.systemchecks;


import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.networktables.NetworkTableEntry;

public class NetworkTablesCheck extends BasicCheck {
    protected static final String SAVE_PATH = "/home/lvuser/logs/networktables";
    protected static final String SAVE_FILE = SAVE_PATH + "testTable.log";
    protected static final String CHECK_NAME = "NetworkTables";
    protected static final double DEFAULT_ENTRY = 4.904;
    protected static NetworkTableInstance inst;
    protected static NetworkTable table;
    protected static NetworkTableEntry entry;
    protected static String[] entries;

    public NetworkTablesCheck(double timeout) {
        super("NetworkTableCheck", timeout, CHECK_NAME);
    }

    public NetworkTablesCheck() {
        this(DEFAULT_TIMEOUT);
    }

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
            updateStatus(CHECK_NAME, SystemStatus.FAIL, e);
        }
        if (entries[0] != Double.toString(DEFAULT_ENTRY)) { // TODO: Check formatting of the entries array
            updateStatus(CHECK_NAME, SystemStatus.FAIL, new Exception("LOADED ARRAY NOT EQUAL TO SAVED ARRAY"));
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}