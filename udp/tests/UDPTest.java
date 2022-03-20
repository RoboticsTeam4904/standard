package org.usfirst.frc4904.standard.udp.tests;


//TODO fix imports
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
import org.usfirst.frc4904.standard.udp.Client;
import org.usfirst.frc4904.standard.udp.Server;
import java.util.concurrent.TimeUnit;

import java.io.*;
import java.util.HashMap;

public class UDPTest {
    Client client;
    TestUDPServer server;
    private int sourcePort = 3375;
    private int receivePort = 8765;
    private int destinationPort = 4321;

    public void setup() {
        System.out.println("Setting up the test on socket #" + receivePort + ".");
        try {
            server = new TestUDPServer(receivePort, "NUS12738-12-aksramks-MacBook-Pro.local");
            server.start();
        client = new Client("NUS12738-12-aksramks-MacBook-Pro.local", sourcePort, destinationPort);
        } catch (IOException ex) {
            System.out.println("ERR: IOException during setup. This error is from creating the Server.");
            ex.printStackTrace();
        }
    }

    public void encode() throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        packer
            .packArrayHeader(2)

            .packArrayHeader(3)

            .packArrayHeader(2)

            
            .packDouble(2.0)
            .packArrayHeader(2)
            .packDouble(1.0)

            .packDouble(1.0)

            .packArrayHeader(2)

            
            .packDouble(2.0)
            .packArrayHeader(2)
            .packDouble(1.0)

            .packDouble(1.0)
            
            .packDouble(0.0)
            .packDouble(0.0);


        packer.close();
        client.sendGenericEcho(packer);
        client.close();
    }
    
    public static void main(String[] args) throws IOException
    {      
        UDPTest udpTest = new UDPTest();
        udpTest.setup();
        udpTest.encode();
        System.out.println(udpTest.server.testThreadGlobal); // This is a little bit sketchy since UDP is technically async
    }
}