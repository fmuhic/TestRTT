package pitcher;

import msg.Message;

public class NetworkMetrics {

    private long msgCount = 0;
    private float avgRTT = 0.0f;
    private float avgAB = 0.0f;
    private float avgBA = 0.0f;

    synchronized public void processMsg(Message msg) {
        msgCount++;

        // s = (a1 + a2 + ... + an) / n
        // s' = s + (an+1 - s) / (n + 1)
        avgRTT = avgRTT + (msg.returnedTime - msg.creationTime - avgRTT) / msgCount;
        avgAB = avgAB + (msg.fetcherTime - msg.creationTime - avgAB) / msgCount;
        avgBA = avgBA + (msg.returnedTime - msg.fetcherTime - avgBA) / msgCount;
    }

    synchronized public void resetMetrics() {
        msgCount = 0;
        avgRTT = 0.0f;
        avgAB = 0.0f;
        avgBA = 0.0f;
    }

    synchronized public float avgRTT() { return avgRTT; }
    synchronized public float avgAB() { return avgAB; }
    synchronized public float avgBA() { return avgBA; }

}
