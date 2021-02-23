//package net.uni.chat.cli.poc;
//
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class ChatServer {
//    public static void main(String[] args) {
//        ServerManager serverManager = new ServerManager();
//        ChatServer chatServer = new ChatServer();
//        chatServer.runServer(serverManager);
//    }
//
//    public void runServer(ServerManager serverManager) {
//        try{
//            System.out.println("Server is accepting connections on 8888 port");
//            ServerSocket serverSocket = new ServerSocket(8888);
//            while(true) {
//                Socket socket = serverSocket.accept();
//                ClientReaderWriter cr = new ClientReaderWriter(socket, this);
//                if(serverManager.set.size() == 0) {
//                    cr.sendMessage(String.format("Congratulations! You are the first member and the Coordinator"));
//                    cr.setRole("Coordinator");
//                } else {
//                    cr.setRole("Member");
//                }
//                serverManager.set.add(cr);
//                cr.start();
//            }
//        }catch (Exception ignore) { }
//    }
//
//}
