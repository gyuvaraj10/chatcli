package net.uni.chat.cli.poc;

import java.io.IOException;
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
            System.out.println("Server is accepting connections on 8888 port");
            ServerSocket serverSocket = new ServerSocket(8888);
            while(true) {
                Socket socket = serverSocket.accept();
                ClientReaderWriter cr = new ClientReaderWriter(socket, this);
                if(set.size() == 0) {
                    cr.sendMessage("Congratulations! You are the first member and the Coordinator");
                    cr.setRole("Coordinator");
                } else {
                    cr.setRole("Member");
                }
                set.add(cr);
                cr.start();
            }

        }catch (Exception ignore) { }
    }

    void removeInActive() {
        set.removeIf(clientReaderWriter -> {
            try {
                int available = clientReaderWriter.getSocket().getInputStream().available();
                if(available == -1) {
                    System.out.println(clientReaderWriter.getUserName() + " Left");
                }
                return available == -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        });
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
        sb.append("Connected Users: ");
        for(ClientReaderWriter r: set) {
            sb.append(r.getUserName()+",");
        }
        return sb.toString();
    }

    void cleanUser(String userName) {
        set.removeIf(x->x.getUserName().equals(userName));
    }
}
