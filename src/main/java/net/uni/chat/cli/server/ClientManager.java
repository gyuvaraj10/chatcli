package net.uni.chat.cli.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientManager implements Runnable {

    private Socket socket;

    public ClientManager (Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String line = dis.readUTF();
            System.out.println(line);
            for(ClientManager clientManager: ChatServer.clientManagers) {
                new DataOutputStream(clientManager.socket.getOutputStream()).writeUTF(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
