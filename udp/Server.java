package org.usfirst.frc4904.robot.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

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
                String received = new String(packet.getData());
                String data = received.substring(8, packet.getLength());
                String header = received.substring(0, 8);
                System.out.println(
                        "Received: '" + data + "', length: " + data.length() + ", from client: '" + header + "'.");
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> map = mapper.readValue(data, Map.class);

                    if (expectedString.equals(map.get("value-1"))
                            && Math.abs((Double) map.get("value-2") - (1D / 3D)) < EPSILON
                            && Math.abs((Double) map.get("value-3") - Math.PI) < EPSILON) {
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
                for (byte byt : data.getBytes("UTF-8")) {
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