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

    /**
     * This method accepts connection/request from each client on 8888 port
     * After the successful connection, the server initiates and assigns a reader and writer
     * processes for each client connected to the server.
     */
    public void runServer() {
        try{
            System.out.println("Server is accepting connections on 8888 port");
            ServerSocket serverSocket = new ServerSocket(8888);
            while(true) {
                Socket socket = serverSocket.accept(); //server will wait until it receives a new connection from the client/user
                ClientReaderWriter cr = new ClientReaderWriter(socket, this); //reader and write object that holds the responsibility of reading and writing to the client
                if(set.size() == 0) {
                    cr.sendMessage("Congratulations! You are the first member and the Coordinator");
                    cr.setRole("Coordinator");
                } else {
                    cr.setRole("Member");
                }
                set.add(cr);
                cr.start();
            }
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * broadcasts the messages to all the clients connected in the group
     * @param message message to be broad casted
     * @param readerWrit client reader writer object
     */
    void broadCastMessage(String message, ClientReaderWriter readerWrit) {
        for(ClientReaderWriter readerWriter: set) {
            if(!readerWriter.getUserName().equalsIgnoreCase(readerWrit.getUserName())) {
                readerWriter.sendMessage(message);
            }
        }
    }

    /**
     * broadcasts the message to requested user only. This method is used for client initiated commands e.g., show users
     * @param readerWrit client's reader and writer object who initiates the command
     */
    void broadCastToRequestedUser(ClientReaderWriter readerWrit) {
        for(ClientReaderWriter readerWriter: set) {
            if(readerWriter.getUserName().equalsIgnoreCase(readerWrit.getUserName())) {
                readerWriter.sendMessage(listUsers());
                break;
            }
        }
    }

    /**
     * List the users/clients connected to the server seperated by comma
     * @return clients seperated by comma
     */
    String listUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connected Users: ");
        for(ClientReaderWriter r: set) {
            sb.append(r.getUserName()+",");
        }
        return sb.toString();
    }

    /**
     * assigns a coordinator role to a client
     */
    void assignCoordinatorRole() {
        for(ClientReaderWriter readerWriter: set) {
            readerWriter.setRole("Coordinator");
            readerWriter.sendMessage("Congratulations! You have now become the Coordinator");
            break;
        }
    }

    /**
     * removes the user if exited from the group
     * @param userName
     */
    void cleanUser(String userName) {
        boolean isUserRemoved = set.removeIf(x->x.getUserName().equals(userName));
        System.out.println(isUserRemoved);
    }
}
