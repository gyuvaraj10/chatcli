package net.uni.chat.cli.poc;

import java.util.HashSet;
import java.util.Set;

public class ServerManager {

    public Set<ClientReaderWriter> set = new HashSet<>();

    public void broadCastMessage(String message, ClientReaderWriter readerWrit) {
        for(ClientReaderWriter readerWriter: set) {
            if(!readerWriter.getUserName().equalsIgnoreCase(readerWrit.getUserName())) {
                readerWriter.sendMessage(message);
            }
        }
    }

    public void broadCastToRequestedUser(ClientReaderWriter readerWrit) {
        for(ClientReaderWriter readerWriter: set) {
            if(readerWriter.getUserName().equalsIgnoreCase(readerWrit.getUserName())) {
                readerWriter.sendMessage(listUsers());
                break;
            }
        }
    }

    public String listUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connected Users: ");
        for(ClientReaderWriter r: set) {
            sb.append(r.getUserName()+"\n");
        }
        return sb.toString();
    }
}
