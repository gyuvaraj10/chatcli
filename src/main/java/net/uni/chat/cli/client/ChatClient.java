package net.uni.chat.cli.client;

import java.io.*;
import java.net.Socket;

public class ChatClient {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8888);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
