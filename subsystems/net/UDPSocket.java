package org.usfirst.frc4904.standard.subsystems.net;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.buffer.ByteBufferInput;
import org.msgpack.core.buffer.MessageBuffer;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.net.message.Packable;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class UDPSocket extends SubsystemBase {
    private static final int RECV_BUFFER_SIZE = 1024;

    private DatagramChannel channel;

    public UDPSocket(SocketAddress address) throws IOException {
        final DatagramChannel channel = DatagramChannel.open().bind(address);
        channel.configureBlocking(false);

        this.channel = channel;
    }

    protected void send(SocketAddress address, Packable message) throws IOException {
        final MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        message.pack(packer);
        packer.close();

        final MessageBuffer messageBuffer = packer.toMessageBuffer();

        channel.send(messageBuffer.sliceAsByteBuffer(), address);
    }

    protected abstract void receive(SocketAddress address, MessageUnpacker unpacker) throws IOException;

    private void pollReceive() throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(RECV_BUFFER_SIZE);
        final MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(buffer);

        while (true) {
            final SocketAddress address = channel.receive(buffer);

            if (address == null) {
                break;
            }

            receive(address, unpacker);

            buffer.clear();
            unpacker.reset(new ByteBufferInput(buffer));
        }

        unpacker.close();
    }

    @Override
    public void periodic() {
        try {
            pollReceive();
        } catch (IOException ex) {
            LogKitten.ex(ex);
        }
    }
}
