package net.uni.chat.cli.poc;

import java.io.Console;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private String userName;

    public static void main(String[] args) {
        Client client = new Client();
        client.userName = args[2];
        Console console = System.console();
        client.runClient(args[0], Integer.parseInt(args[1]), console);
    }

    public void runClient(String host, int port, Console console) {
        try{
            Socket s=new Socket(host,port);
            WriterThread writerThread = new WriterThread(s, userName, console);
            writerThread.start();
            ReaderThread readerThread =new ReaderThread(s, this);
            readerThread.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println(userName);
                    writerThread.setDoNotTerminate(false);
                    readerThread.setDoNotTerminate(false);
                    writerThread.publishShutdown();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
