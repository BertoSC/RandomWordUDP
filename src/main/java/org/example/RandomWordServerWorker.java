package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class RandomWordServerWorker implements Runnable{
    private DatagramSocket udpSocket;
    byte [] buffer = new byte[1024];
    private final String URL= "https://random-word-api.herokuapp.com/word?length=";
    private final String DEFAULT_URL= "https://random-word-api.herokuapp.com/word?length=5";

    public RandomWordServerWorker(DatagramSocket udpSocket){
        this.udpSocket=udpSocket;
    }

    private String solicitarAPI(String [] args) {
        URL peticion = null;
        try {
            new URI(DEFAULT_URL).toURL();
            HttpURLConnection con = (HttpURLConnection) peticion.openConnection();
            con.setRequestMethod("GET");
            StringBuilder json = new StringBuilder();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting().create();
            if (con.getResponseCode()== HttpURLConnection.HTTP_OK){
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))){
                    String line;
                    while ((line=br.readLine())!=null){
                        json.append(line);
                    }
                }
            }
            String palabra = gson.fromJson(json, );



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
        DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
        udpSocket.receive(peticion);
        String palabra = new String(peticion.getData(), 0, peticion.getLength(), "UTF-8");
        String [] args = palabra.split(" ");
        String palabra = solicitarAPI(args);
            
        }        
    }




}
