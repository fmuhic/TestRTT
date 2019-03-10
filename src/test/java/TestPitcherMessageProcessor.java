import msg.Message;
import msg.PitcherMessageProcessor;
import org.junit.jupiter.api.Test;
import util.DateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestPitcherMessageProcessor {
    @Test
    public void updateMessage() {
        DateTime dt = mock(DateTime.class);
        when(dt.currentTimeMs()).thenReturn(123456789l);
        Message msg = new Message(1l, 111l, 222l, 333l, "hello");
        PitcherMessageProcessor pmp = new PitcherMessageProcessor(dt);

        pmp.update(msg);

        assertEquals(1l, msg.id);
        assertEquals(111l, msg.creationTime);
        assertEquals(222l, msg.fetcherTime);
        assertEquals(123456789l, msg.returnedTime);
        assertEquals("hello", msg.content);
    }
}
