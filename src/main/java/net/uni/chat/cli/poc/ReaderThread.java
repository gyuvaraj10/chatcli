package net.uni.chat.cli.poc;

import java.io.*;
import java.net.Socket;

public class ReaderThread extends Thread{
    private BufferedReader reader;

    private Socket socket;
    private Client client;

    public ReaderThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true) {
            try {
                System.out.println("\n"+reader.readLine());
            } catch (IOException ignore) {
                System.out.println(ignore);
                break;
            }
        }
    }
}
