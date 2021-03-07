package net.uni.chat.cli.poc;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class WriterThread extends Thread {

    private PrintWriter writer;
    private String userName;
    private Console console;
    private boolean doNotTerminate = true;

    public WriterThread(Socket socket,  String userName, Console console) {
        try {
            this.userName = userName;
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            this.console = console;
        } catch (IOException ex) {
            throw new RuntimeException("Error getting output stream: " + ex.getMessage());
        }
    }

    public void run() {
        try {
            writeMessage();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isDoNotTerminate() {
        return doNotTerminate;
    }

    public void setDoNotTerminate(boolean doNotTerminate) {
        this.doNotTerminate = doNotTerminate;
    }


    public void writeMessage() throws UnknownHostException  {
        InetAddress myIP=InetAddress.getLocalHost();
        writer.println(String.format("Server: New User joined %s,%s",userName, myIP.getHostAddress()));
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
