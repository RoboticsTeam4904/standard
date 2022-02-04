package org.usfirst.frc4904.standard.udp;


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

import java.io.*;
import java.util.HashMap;

public class UDPTest {
    Client client;
    private int socketNum = 3375;

    public void setup() {
        System.out.println("Setting up the test on socket #" + socketNum + ".");
        try {
            new Server(socketNum).start();
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
        System.out.println(client.sendGenericEcho(packer));
        client.close();
    }

    public static void main(String[] args) throws IOException
    {
        // MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        
        // packer.packString("test")
        //         .packString("leo")
        //         .packArrayHeader(2)
        //         .packString("xxx-xxxx")
        //         .packString("yyy-yyyy");
                
        // packer.close(); // Never forget to close (or flush) the buffer

        // byte[] s = "utf-8 strings".getBytes(MessagePack.UTF8);
        // System.out.println(s);
        // packer.packRawStringHeader(s.length);
        // packer.writePayload(s);
        // System.out.println(s + " WT");
        
        
        UDPTest udpTest = new UDPTest();
        udpTest.setup();
        udpTest.test();
    }
}