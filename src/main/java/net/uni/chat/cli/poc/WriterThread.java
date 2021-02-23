package net.uni.chat.cli.poc;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class WriterThread extends Thread{

    private PrintWriter writer;
    private Client client;
    private String userName;

    public WriterThread(Socket socket, Client client, String userName) {
        try {
            this.userName = userName;
            this.client = client;
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        Console console = System.console();
        try {
            InetAddress myIP=InetAddress.getLocalHost();
            writer.println(String.format("New User joined %s,%s",userName, myIP.getHostAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        while(true) {
            String text = console.readLine(userName+":");
            if(!text.equalsIgnoreCase("")) {
                writer.println(userName+":"+text);
            }
        }
    }
}
