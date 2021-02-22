package net.uni.chat.cli.poc;

import java.io.*;
import java.net.Socket;

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
//        String userName = console.readLine("\nEnter your name: ");
//        client.setUserName(userName);
        writer.println(String.format("New User joined %s",userName));
        while(true) {
            String text = console.readLine(userName+":");
            if(!text.equalsIgnoreCase("")) {
                writer.println(userName+":"+text);
            }
        }
    }
}
