package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;

public class RandomWordServerWorker implements Runnable{
    private DatagramSocket udpSocket;
    byte [] buffer = new byte[1024];
    private final String URL= "https://random-word-api.herokuapp.com/word?length=";
    private final String DEFAULT_URL= "https://random-word-api.herokuapp.com/word?length=5";
    Type listString = new TypeToken<List<String>>(){}.getType();

    public RandomWordServerWorker(DatagramSocket udpSocket){
        this.udpSocket=udpSocket;
    }

    private String solicitarAPI(String [] args) {
        URL peticion = null;
        try {
            peticion = new URI(DEFAULT_URL).toURL();
            HttpURLConnection con = (HttpURLConnection) peticion.openConnection();
            con.setRequestMethod("GET");
            StringBuilder json = new StringBuilder();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            if (con.getResponseCode()== HttpURLConnection.HTTP_OK){
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))){
                    String line;
                    while ((line=br.readLine())!=null){
                        json.append(line);
                    }
                }
            }
           List<String> palabras = gson.fromJson(json.toString(), listString);
            return palabras.get(0);



        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void run() {
        try {
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(peticion);
            String comando = new String(peticion.getData(), 0, peticion.getLength(), "UTF-8");
            String[] args = comando.split(" ");
            String wordResponse = solicitarAPI(args);
            int clientPort = peticion.getPort();
            InetAddress address = peticion.getAddress();
            buffer = wordResponse.getBytes();
            DatagramPacket answer = new DatagramPacket(buffer, buffer.length, address, clientPort);
            udpSocket.send(answer);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    }





