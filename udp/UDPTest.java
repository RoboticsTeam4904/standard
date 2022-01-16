package org.usfirst.frc4904.robot.udp;

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

    public void test() {
        HashMap<String, Object> testMap = new HashMap<String, Object>();
        testMap.put("value-1", "test");
        float val_2 = 1f/3f;
        testMap.put("value-2", val_2);
        testMap.put("value-3", Math.PI);
        System.out.println(client.sendGenericEcho(testMap));
        client.close();
    }

    public static void main(String[] args) {
        UDPTest udpTest = new UDPTest();
        udpTest.setup();
        udpTest.test();
    }
}