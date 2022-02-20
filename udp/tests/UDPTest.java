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

import java.io.*;
import java.util.HashMap;

public class UDPTest {
    Client client;
    private int socketNum = 3375;

    public void setup() {
        System.out.println("Setting up the test on socket #" + socketNum + ".");
        try {
            new TestUDPServer(socketNum).start();
            client = new Client("CLIENT##", socketNum);
        } catch (IOException ex) {
            System.out.println("ERR: IOException during setup. This error is from creating the Server.");
            ex.printStackTrace();
        }
    }

    public void test() throws IOException{
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        packer
                .packInt(1)
                .packString("emacs")
                .packArrayHeader(2)
                .packString("vim")
                .packString("nano");
        packer.close();
        client.sendGenericEcho(packer);
        client.close();
    }

    public static void main(String[] args) throws IOException
    {      
        UDPTest udpTest = new UDPTest();
        udpTest.setup();
        udpTest.test();
    }
}