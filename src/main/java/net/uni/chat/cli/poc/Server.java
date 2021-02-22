package net.uni.chat.cli.poc;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    static Set<ClientReaderWriter> set = new HashSet<>();

    public static void main(String[] args) {
       Server server = new Server();
       server.runServer();
    }

    public void runServer() {
        try{
            ServerSocket serverSocket= new ServerSocket(8888);
            while(true) {
                Socket socket = serverSocket.accept();
                ClientReaderWriter clientReaderWriter = new ClientReaderWriter(socket, this);
                set.add(clientReaderWriter);
                clientReaderWriter.start();
            }
        }catch (Exception ignore) { }
    }
    void broadCastMessage(String message, ClientReaderWriter readerWrit) {
        for(ClientReaderWriter readerWriter: set) {
            if(!readerWriter.getUserName().equalsIgnoreCase(readerWrit.getUserName())) {
                readerWriter.sendMessage(message);
            }
        }
    }
}
