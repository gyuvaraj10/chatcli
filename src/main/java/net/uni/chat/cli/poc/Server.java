package net.uni.chat.cli.poc;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Server {

    static Set<ClientReaderWriter> set = new HashSet<>();

    public static void main(String[] args) {
       Server server = new Server();
       server.runServer();
    }

    public void runServer() {
        try{
            System.out.println("Server is accepting connections at 8888 port");
            ServerSocket serverSocket= new ServerSocket(8888);
            while(true) {
                Socket socket = serverSocket.accept();
                ClientReaderWriter cr = new ClientReaderWriter(socket, this);
                if(set.size() == 0) {
                    cr.sendMessage("Hi you are the first member in the group");
                }
                set.add(cr);
                cr.start();
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

    void broadCastToRequestedUser(ClientReaderWriter readerWrit) {
        for(ClientReaderWriter readerWriter: set) {
            if(readerWriter.getUserName().equalsIgnoreCase(readerWrit.getUserName())) {
                readerWriter.sendMessage(listUsers());
                break;
            }
        }
    }

    String listUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connected Users:\n");
        for(ClientReaderWriter r: set) {
            sb.append(r.getUserName()+"\n");
        }
        return sb.toString();
    }
}
