package org.example;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class RandomWordClient {
    final static int PORT = 50000;

    public static void main(String[] args) {
    byte [] buffer = new byte[1024];
    byte [] bufferRespuesta = new byte[1024];
    try {
        InetAddress serverAdress = InetAddress.getByName("localhost");
        DatagramSocket socketUDP = new DatagramSocket();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your petition to the server: ");
        String petition = sc.nextLine();
        buffer = petition.getBytes();

        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, serverAdress, PORT);
        socketUDP.send(paquete);

        DatagramPacket respuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
        socketUDP.receive(respuesta);
        String palabra = new String(respuesta.getData(), 0, respuesta.getLength(), "UTF-8");
        System.out.println(palabra);
        socketUDP.close();

    } catch (SocketException ex){

    } catch (UnknownHostException ex){

    }catch (IOException ex){

    }

    }
}
