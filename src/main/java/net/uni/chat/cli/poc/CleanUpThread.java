package net.uni.chat.cli.poc;

public class CleanUpThread extends Thread {

    private Server server;

    public CleanUpThread(Server server) {
        this.server = server;
    }

    public void run() {
        while(true) {
            this.server.removeInActive();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}