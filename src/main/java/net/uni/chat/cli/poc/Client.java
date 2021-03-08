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

    /**
     * runs console writer thread and reader thread in parallel which allows the client to write and read
     * @param host hostname of the server to connect
     * @param port port number of the server to connect
     * @param console console object to be used
     */
    public void runClient(String host, int port, Console console) {
        try{
            Socket s=new Socket(host,port); //sends the request to the server
            WriterThread writerThread = new WriterThread(s, userName, console); //writes the messages
            writerThread.start();
            ReaderThread readerThread =new ReaderThread(s, System.out); //receives the messages
            readerThread.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println(userName);
                    writerThread.setDoNotTerminate(false); //terminate the writer loop /thread
                    readerThread.setDoNotTerminate(false); //terminate the reader loop/thread
                    writerThread.publishShutdown(); //send 'User Left the group' message to the server and
                    // the server will broadcast this message to the other members of the group automatically
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
