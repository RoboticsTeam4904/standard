package org.usfirst.frc4904.standard.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.Arrays;


import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePack.PackerConfig;
import org.msgpack.core.MessagePack.UnpackerConfig;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ArrayValue;
import org.msgpack.value.ExtensionValue;
import org.msgpack.value.FloatValue;
import org.msgpack.value.IntegerValue;
import org.msgpack.value.TimestampValue;
import org.msgpack.value.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

interface MessageDecodeListener {
    Void decode();
}

public class Server extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];
    protected String expectedString = "test";
    protected String serverHeader = "##SERVER";
    private final Double EPSILON = 0.0001;

    public Server(int socketNum) throws IOException {
        socket = new DatagramSocket(socketNum);
    }

    public void run() {
        System.out.println("Server running.");
        running = true;
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                byte[] received = packet.getData();
                byte[] data = Arrays.copyOfRange(received, 8, packet.getLength());
                String header = new String(Arrays.copyOfRange(received, 0, 8));
                System.out.println(
                        "Received: '" + data + "', length: " + data.length + ", from client: '" + header + "'.");
                try {
                    MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
                    //unpacker.unp
                    int firstInteger = unpacker.unpackInt();
                    String firstString = unpacker.unpackString();             // 1
                    int numberOfStuff = unpacker.unpackArrayHeader();  // 2
                    String[] terminalTextEditors = new String[numberOfStuff];
                    for (int i = 0; i < numberOfStuff; ++i) {
                        terminalTextEditors[i] = unpacker.unpackString();   // terminalTextEditors = {"vim", "nano"}
                    }
                    unpacker.close();
                    
                    System.out.println(String.format("Integer:%d String:%s ArrayElementOne: %s ArrayElementTwo: %s",firstInteger, firstString, terminalTextEditors[0], terminalTextEditors[1]));
                    if (terminalTextEditors[0] ==  "vim") {
                        System.out.println("It's a SUCCESS!");
                        running = false;
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] tempArr = new byte[buf.length];
                int index = 0;
                for (byte byt : this.serverHeader.getBytes("UTF-8")) {
                    tempArr[index] = byt;
                    index++;
                }
                for (byte byt : data) {
                    tempArr[index] = byt;
                    index++;
                }
                packet = new DatagramPacket(tempArr, tempArr.length, address, port);
                socket.send(packet);
                buf = new byte[256];
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
}