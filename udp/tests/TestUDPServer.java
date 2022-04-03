package org.usfirst.frc4904.standard.udp.tests;

import java.io.IOException;

import org.usfirst.frc4904.standard.udp.Server;

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


public class TestUDPServer extends Server {
    String testThreadGlobal;
    TestUDPServer(int SocketNum, String hostname) throws IOException{
        super(SocketNum, hostname);
    }

    protected void decode(byte[] data) throws IOException{
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
        int firstInteger = unpacker.unpackInt();
        String firstString = unpacker.unpackString();             // 1
        int numberOfStuff = unpacker.unpackArrayHeader();  // 2
        String[] terminalTextEditors = new String[numberOfStuff];
        for (int i = 0; i < numberOfStuff; ++i) {
                terminalTextEditors[i] = unpacker.unpackString();   // terminalTextEditors = {"vim", "nano"}
        }
        unpacker.close();
        
        System.out.println(String.format("Integer: %d String: %s ArrayElementOne: %s ArrayElementTwo: %s",firstInteger, firstString, terminalTextEditors[0], terminalTextEditors[1]));
        if (terminalTextEditors[0] ==  "vim") {
            System.out.println("It's a SUCCESS!");
            running = false;
        }
        testThreadGlobal = String.format("eeeeemaaacs and also %d", firstInteger);
    }
}
