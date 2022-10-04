package org.usfirst.frc4904.standard.subsystems.net.message;

import java.io.IOException;

import org.msgpack.core.MessagePacker;

public interface Packable {
    void pack(MessagePacker packer) throws IOException;
}
