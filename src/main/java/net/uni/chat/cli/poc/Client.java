package net.uni.chat.cli.poc;

import java.net.Socket;

public class Client {

    private String userName;

    public static void main(String[] args) {
        Client client = new Client();
        client.runClient();
    }

    public void runClient() {
        try{
            Socket s=new Socket("localhost",8888);
            new WriterThread(s, this).start();
            new ReaderThread(s, this).start();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }
}
