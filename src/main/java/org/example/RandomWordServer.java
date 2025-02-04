package org.example;

import java.net.DatagramSocket;

public class RandomWordServer {
    final static int PORT = 50000;

    public static void main(String[] args) {
        DatagramSocket udpSocket = new DatagramSocket(PORT);
        while (true){
            Thread th = new Thread(new RandomWordServerWorker(udpSocket));
            th.start();
        }

    }


}
