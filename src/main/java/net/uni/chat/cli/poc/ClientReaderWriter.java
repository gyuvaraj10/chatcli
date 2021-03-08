package net.uni.chat.cli.poc;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientReaderWriter extends Thread {

    private Server server;
    private PrintWriter writer;
    private String userName;
    private String role;
    private BufferedReader bufferedReader;
    private CommandRunner commandRunner;

    /**
     * Initializes the client specific writer object to write the data to client
     * @param socket client socket object
     * @param server server object to utilize the server operations
     * @throws IOException
     */
    public ClientReaderWriter(Socket socket, Server server) throws IOException {
        this.server = server;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream outputStream = socket.getOutputStream();
        commandRunner = new CommandRunner(server, this);
        writer = new PrintWriter(outputStream, true);
    }

    /**
     * this collects the messages sent by each client and broadcasts the messages/commands(show users, bye)
     * to all the clients
     * This operation runs in an independent thread for each client
     */
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
                    commandRunner.runCommand(message);
                } else {
                    System.out.println(new Date().toString()+" "+message);
                    server.broadCastMessage(message, this);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
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
