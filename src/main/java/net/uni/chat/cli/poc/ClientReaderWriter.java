package net.uni.chat.cli.poc;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientReaderWriter extends Thread{

    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private String userName;
    private String role;
    private BufferedReader bufferedReader;
    private CommandRunner commandRunner;

    public ClientReaderWriter(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();
            commandRunner = new CommandRunner(server, this);
            writer = new PrintWriter(outputStream, true);
        } catch (Exception ignore) {}
    }

    public void run() {
        try {
            String message = bufferedReader.readLine();
            System.out.println(message);
            String userNamePrefix = "Server: New User joined ";
            if(message.startsWith(userNamePrefix)) {
                String username = message.split(userNamePrefix)[1];
                this.setUserName(username);
                server.broadCastMessage(message, this);
            }
            while(true) {
                message = bufferedReader.readLine();
                if(commandRunner.isACommand(message.split(":")[1])) {
                    if(this.getRole().equals("Coordinator")) {
                        commandRunner.runCommand(message);
                    }
                } else {
                    System.out.println(new Date().toString()+" "+message);
                    server.broadCastMessage(message, this);
                }
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

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
