import msg.MessageFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.DateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// I'm gonna leave these 2 awful tests as a reminder to never, ever and in any situation use static, anywhere.
// I used it in one place (MessageFactory class) to give it another shot and I regretted it.
public class TestMessageFactory {

    private static String firstMsg;

    @BeforeAll
    public static void createFirstMessage() {
        DateTime dt = mock(DateTime.class);
        when(dt.currentTimeMs()).thenReturn(123456789l);
        MessageFactory mf = new MessageFactory(130, 'x', dt);
        firstMsg = mf.create();
    }

    @Test
    public void createMessage() {
        String expectedFirstMsg = "{\"id\":10000000000001," +
                                  "\"creationTime\":123456789," +
                                  "\"fetcherTime\":-111111111111," +
                                  "\"returnedTime\":-111111111111," +
                                  "\"content\":\"xxxxxxxxxx\"}";

        assertEquals(expectedFirstMsg, firstMsg);
    }

    @Test
    public void testMessageSize() {
        DateTime dt = mock(DateTime.class);
        when(dt.currentTimeMs()).thenReturn(1552257320705l);

        MessageFactory mf1 = new MessageFactory(1, 'x', dt);
        MessageFactory mf2 = new MessageFactory(300, 'x', dt);

        String msg1 = mf1.create();
        String msg2 = mf2.create();

        assertTrue(1 < MessageFactory.minMsgLength() && MessageFactory.minMsgLength() < 300);
        assertEquals(120, msg1.getBytes().length);
        assertEquals(300, msg2.getBytes().length);
    }
}
