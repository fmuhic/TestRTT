import msg.MessageValidator;
import org.junit.jupiter.api.Test;
import pitcher.Logger;
import pitcher.NetworkMetrics;
import util.DateTime;
import util.Printer;

import static org.mockito.Mockito.*;

public class TestLogger {

    @Test
    public void testLogMetrics() {
        DateTime dt = mock(DateTime.class);
        when(dt.currentDateTime()).thenReturn("03-03-2019 00:00:00.000");

        NetworkMetrics nm = mock(NetworkMetrics.class);
        when(nm.avgRTT()).thenReturn(25.0f);
        when(nm.avgAB()).thenReturn(10.0f);
        when(nm.avgBA()).thenReturn(15.0f);

        MessageValidator mv = mock(MessageValidator.class);
        when(mv.newMessagesReceived()).thenReturn(50);
        when(mv.oldMessagesReceived()).thenReturn(75);
        when(mv.overallMessageCount()).thenReturn(100l);
        when(mv.sentMessagesCount()).thenReturn(50);

        Printer pr = mock(Printer.class);

        Logger logger = new Logger(pr, dt);
        logger.logMetrics(nm, mv);

        String firstMsg = "03-03-2019 00:00:00.000: OverallMessageCount: 100, " +
                           "MessagesSent: 50, MessagesReceived: 50, OldMessagesReceived: 75";
        String secondMsg = "03-03-2019 00:00:00.000: RTT: 25.0, A->B: 10.0, B->A: 15.0";

        verify(pr, times(1)).print(firstMsg);
        verify(pr, times(1)).print(secondMsg);
    }
}
