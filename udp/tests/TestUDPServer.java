package org.usfirst.frc4904.standard.udp.tests;

import java.io.IOException;

import org.usfirst.frc4904.standard.udp.Server;

public class TestUDPServer extends Server {

    TestUDPServer(int SocketNum) throws IOException{
        super(SocketNum);
    }
    protected void decode(byte[] e){
        
    }
}
