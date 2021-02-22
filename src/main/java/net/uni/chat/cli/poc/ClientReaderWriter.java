package net.uni.chat.cli.poc;

import java.io.*;
import java.net.Socket;

public class ClientReaderWriter extends Thread{

    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private String userName;

    public ClientReaderWriter(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
            while(true) {
                String line = bufferedReader.readLine();
                server.broadCastMessage(line);
            }
        } catch (Exception ignore) {
        }
    }

    public void sendMessage(String message) {
        try {
            writer.println(message);
        } catch (Exception ignore) {
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }
}
