package org.example;

import java.net.DatagramSocket;
import java.net.SocketException;

public class RandomWordServer {
    final static int PORT = 50000;

    public static void main(String[] args) {
        DatagramSocket udpSocket = null;
        try {
            udpSocket = new DatagramSocket(PORT);
            while (true){
                Thread th = new Thread(new RandomWordServerWorker(udpSocket));
                th.start();
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }


}
