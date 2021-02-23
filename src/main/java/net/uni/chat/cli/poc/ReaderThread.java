package net.uni.chat.cli.poc;

import java.io.*;
import java.net.Socket;

public class ReaderThread extends Thread{
    private BufferedReader reader;
    private DataInputStream din;

    private Socket socket;
    private Client client;

    public boolean isDoNotTerminate() {
        return doNotTerminate;
    }

    public void setDoNotTerminate(boolean doNotTerminate) {
        this.doNotTerminate = doNotTerminate;
    }

    private boolean doNotTerminate = true;

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
        while(isDoNotTerminate()) {
            try {
                System.out.println("\n"+reader.readLine());
            } catch (IOException ignore) {
                System.out.println(ignore);
                break;
            }
        }
    }
}
