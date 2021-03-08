package net.uni.chat.cli.poc;

import java.io.*;
import java.net.Socket;

public class ReaderThread extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private  PrintStream printStream;

    public ReaderThread(Socket socket, PrintStream printStream) {
        this.socket = socket;
        this.printStream = printStream;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isDoNotTerminate() {
        return doNotTerminate;
    }

    public void setDoNotTerminate(boolean doNotTerminate) {
        this.doNotTerminate = doNotTerminate;
    }

    private boolean doNotTerminate = true;

    public void run() {
        readMessage();
    }

    public void readMessage() {
        while(isDoNotTerminate()) {
            try {
                printStream.println("\n"+reader.readLine());
            } catch (IOException ignore) {
                System.out.println(ignore);
                break;
            }
        }
    }
}
