package org.usfirst.frc4904.standard.subsystems.net.message;

import java.io.IOException;

import org.msgpack.core.MessageUnpacker;

public interface Unpackable {
    void unpack(MessageUnpacker unpacker) throws IOException;
}
