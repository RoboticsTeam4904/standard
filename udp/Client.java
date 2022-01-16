package org.usfirst.frc4904.robot.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public String sendGenericEcho(HashMap<String, Object> map) {
        String json = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(map);
            // json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            // ^ not compact json
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        byte[] convertedMap = json.getBytes();
        System.out.println("Sending Echo: " + "'" + json + "'.");
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