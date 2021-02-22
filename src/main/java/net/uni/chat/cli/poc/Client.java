package net.uni.chat.cli.poc;

import java.net.Socket;

public class Client {

    private String userName;

    public static void main(String[] args) {
        Client client = new Client();
        client.userName = args[2];
        client.runClient(args[0], Integer.parseInt(args[1]));
    }

    public void runClient(String host, int port) {
        try{
            Socket s=new Socket(host,port);
            new WriterThread(s, this, userName).start();
            new ReaderThread(s, this).start();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
