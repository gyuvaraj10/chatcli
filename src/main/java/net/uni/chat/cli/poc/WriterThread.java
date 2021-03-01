package net.uni.chat.cli.poc;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class WriterThread extends Thread{

    private PrintWriter writer;
    private Client client;
    private String userName;
    Console console;

    public boolean isDoNotTerminate() {
        return doNotTerminate;
    }

    public void setDoNotTerminate(boolean doNotTerminate) {
        this.doNotTerminate = doNotTerminate;
    }

    private boolean doNotTerminate = true;

    public WriterThread(Socket socket, Client client, String userName) {
        try {
            this.userName = userName;
            this.client = client;
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            console = System.console();
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            InetAddress myIP=InetAddress.getLocalHost();
            writer.println(String.format("Server: New User joined %s,%s",userName, myIP.getHostAddress()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(isDoNotTerminate()) {
            String text = console.readLine(userName+":");
            if(!text.equalsIgnoreCase("")) {
                writer.println(userName+":"+text);
            };
        }
    }

    public void publishShutdown() {
        writer.println(userName+":bye");
    }
}
