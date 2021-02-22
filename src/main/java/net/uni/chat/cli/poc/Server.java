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
    void broadCastMessage(String message) {
        for(ClientReaderWriter readerWriter: set) {
            String userNamePrefix = "New User joined ";
            if(message.startsWith(userNamePrefix)) {
                String username = message.split(userNamePrefix)[1];
                readerWriter.setUserName(username);
            }
            if(readerWriter.getUserName() != null && !message.equals(userNamePrefix+readerWriter.getUserName())) {
                readerWriter.sendMessage(readerWriter.getUserName()+":"+message);
            }
        }
    }
}
