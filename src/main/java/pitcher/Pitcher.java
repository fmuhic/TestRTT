package pitcher;

import abstraction.Startable;

public class Pitcher implements Startable {

    private Runnable pinger;

    public Pitcher(Runnable pinger) {
        this.pinger = pinger;
    }

    public void start() {
        Thread pingDaemon = new Thread(pinger);
        try {
            pingDaemon.start();
            pingDaemon.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
