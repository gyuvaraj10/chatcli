package net.uni.chat.cli.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

    static Set<ClientManager> clientManagers = new HashSet<ClientManager>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Connected");
                System.out.println(socket);
                ClientManager clientManager = new ClientManager(socket);
                clientManagers.add(clientManager);
                Thread thread = new Thread(clientManager);
                thread.start();
                thread.join();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
