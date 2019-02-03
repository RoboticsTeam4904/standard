package org.usfirst.frc4904.standard.commands.systemchecks;


public class DefaultException extends Exception {
    protected static final long serialVersionUID = 4902L;
    protected static final String DEFAULT_MESSAGE = "NO ERRORS";

    public DefaultException(String message) {
        super(message);
    }

    public DefaultException() {
        this(DEFAULT_MESSAGE);
    }
}
