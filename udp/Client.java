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
import org.usfirst.frc4904.standard.LogKitten;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.time.Instant;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] header;
    private int socketNum;

    private byte[] buf;

    public Client(String hostname, int sourcePort, int socketNum) {
        try {
            address = InetAddress.getByName(hostname);
            socket = new DatagramSocket(sourcePort);

            this.socketNum = socketNum;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEcho(String msg) {
        //System.out.println("Sending Echo: " + "'" + msg + "'.");
        DatagramPacket packet = null;
        try {
            byte[] buf = msg.getBytes("UTF-8");
            packet = new DatagramPacket(buf, buf.length, address, socketNum);
            socket.send(packet);
            // packet = new DatagramPacket(buf, buf.length);
            // socket.receive(packet);
        } catch (IOException e) {
            LogKitten.wtf("Echo failed" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendGenericEcho(MessageBufferPacker map) {
        byte[] convertedMap;
        convertedMap = map.toByteArray();

        System.out.println("Sending Echo: " + "'" + new String(convertedMap, StandardCharsets.US_ASCII) + "'.");
        DatagramPacket packet = null;
        try {
            byte[] buf = convertedMap;
            System.out.println(buf);
            packet = new DatagramPacket(buf, buf.length, address, socketNum);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Echo failed");
            e.printStackTrace();
        }
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
