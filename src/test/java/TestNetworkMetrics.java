import msg.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pitcher.NetworkMetrics;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNetworkMetrics {

    private NetworkMetrics nm;

    @BeforeEach
    public void setUp() {

        Message msg1 = new Message(1l, 100l, 250l, 300l, "hello");
        Message msg2 = new Message(2l, 100l, 150l, 200l, "hello");

        nm = new NetworkMetrics();
        nm.processMsg(msg1);
        nm.processMsg(msg2);
    }

    @Test
    public void testProcessMsg() {

        assertEquals(150.0f, nm.avgRTT());
        assertEquals(100.0f, nm.avgAB());
        assertEquals(50.0f, nm.avgBA());
    }

    @Test
    public void testResetMetrics() {

        nm.resetMetrics();

        assertEquals(0.0f, nm.avgRTT());
        assertEquals(0.0f, nm.avgAB());
        assertEquals(0.0f, nm.avgBA());
    }
}
