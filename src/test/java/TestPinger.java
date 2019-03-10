import msg.MessageFactory;
import org.junit.jupiter.api.Test;
import pitcher.MessageSender;
import pitcher.Pinger;
import pitcher.PitcherCoordinator;
import util.DateTime;

import static org.mockito.Mockito.*;

public class TestPinger {
    @Test
    public void testPing() {
        MessageSender msgService = mock(MessageSender.class);
        PitcherCoordinator coordinator = mock(PitcherCoordinator.class);
        DateTime dateTime = mock(DateTime.class);
        when(dateTime.currentTimeMs()).thenReturn(123456789l);
        MessageFactory messageFactory = mock(MessageFactory.class);
        when(messageFactory.create()).thenReturn("Message");

        int msgsPerSec = 5;
        Pinger pinger = new Pinger(msgService, msgsPerSec, messageFactory, coordinator, dateTime);

        pinger.ping();

        verify(coordinator, times(1)).update(anyLong(), anyLong());
        verify(messageFactory, times(5)).create();
        verify(msgService, times(5)).send("Message", coordinator);
        verify(coordinator, times(5)).validateSentMessage(anyLong());
    }
}
