package org.usfirst.frc4904.standard.udp;

import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;

import java.io.IOException;


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
import java.math.BigInteger;
import java.time.Instant;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

interface OnMessageRecieveEventListener {
    Void decode();
}

public class Client {
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] header;
    private int socketNum;

    private byte[] buf;

    public Client(String header, int socketNum) {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            this.socketNum = socketNum;
            this.header = header.getBytes("UTF-8");
            if (this.header.length > 8) {
                this.header = Arrays.copyOfRange(this.header, 0, 8);
            } else if (this.header.length < 8) {
                byte[] tempArr = new byte[8];
                for (int index = 0; index < this.header.length; index++) {
                    tempArr[index] = this.header[index];
                }
                for (int index = this.header.length; index < 8; index++) {
                    tempArr[index] = "#".getBytes("UTF-8")[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendEcho(String msg) {
        System.out.println("Sending Echo: " + "'" + msg + "'.");
        DatagramPacket packet = null;
        try {
            byte[] tempArr = new byte[msg.length() + 8];
            int index = 0;
            for (byte byt : header) {
                tempArr[index] = byt;
                index++;
            }
            for (byte byt : msg.getBytes("UTF-8")) {
                tempArr[index] = byt;
                index++;
            }
            buf = tempArr;
            packet = new DatagramPacket(buf, buf.length, address, socketNum);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
        } catch (IOException e) {
            System.out.println("Echo failed");
            e.printStackTrace();
        }
        String received = new String(packet.getData());
        String data = received.substring(8, packet.getLength());
        String header = received.substring(0, 8);
        received = ("Received back: '" + data + "', length: " + data.length() + ", from server: '" + header + "'.");
        return received;
    }

    public String sendGenericEcho(MessageBufferPacker map) {
        byte[] convertedMap;
        convertedMap = map.toByteArray();

        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(convertedMap);

                // try {
                //     //unpacker.unp
                //     int thingy = unpacker.unpackInt();
                //     System.out.println(thingy);
                //     String thing1 = unpacker.unpackString();             // 1
                //     System.out.println(thing1 + "but meacs");
                //     int numberOfStuff = unpacker.unpackArrayHeader();  // 2
                //     String[] terminalTextEditors = new String[numberOfStuff];
                    
                //     for (int i = 0; i < numberOfStuff; ++i) {
                //         terminalTextEditors[i] = unpacker.unpackString();   // terminalTextEditors = {"vim", "nano"}
                //     }
                //     unpacker.close();
                //     System.out.println(String.format("thingy:%d thing1:%s thing2:%s", thingy, thing1, terminalTextEditors[1]));

                // } catch (IOException e) {
                //     System.out.println("unfortunate");
                // }
                    

        System.out.println("Sending Echo: " + "'" + new String(convertedMap, StandardCharsets.US_ASCII) + "'.");
        DatagramPacket packet = null;
        try {
            byte[] tempArr = new byte[convertedMap.length + 8];
            int index = 0;
            for (byte byt : header) {
                tempArr[index] = byt;
                index++;
            }
            for (byte byt : convertedMap) {
                tempArr[index] = byt;
                index++;
            }
            buf = tempArr;
            System.out.println(buf);
            packet = new DatagramPacket(buf, buf.length, address, socketNum);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
        } catch (IOException e) {
            System.out.println("Echo failed");
            e.printStackTrace();
        }
        String received = new String(packet.getData());
        String data = received.substring(8, packet.getLength());
        String header = received.substring(0, 8);
        System.out.println("sand");
        received = ("Received back: '" + data + "', length: " + data.length() + ", from server: '" + header + "'.");
        System.out.println("which");
        return received;
    }

    public String receiveData() {
        DatagramPacket packet = new DatagramPacket(new byte[256], 256);
        try {
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            return received;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        socket.close();
    }
}
