import msg.Message;
import msg.MessageValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMessageValidator {

    private MessageValidator messageValidator;
    private Message msg1;
    private Message msg2;
    private Message msg3;

    @BeforeEach
    public void setUp() {

        messageValidator = new MessageValidator(1000l, 2000l);

        msg1 = new Message(1000l, 100l, 250l, 300l, "hello");
        msg2 = new Message(1555l, 100l, 150l, 200l, "hello");
        // msg3 is old since it's ID is below currently active message range (1000 - 2000)
        msg3 = new Message(3l, 200l, 270l, 355l, "hello");
    }

    @Test
    public void testValidateReceivedMessage() {

        messageValidator.validateReceivedMessage(msg1);
        messageValidator.validateReceivedMessage(msg2);
        messageValidator.validateReceivedMessage(msg3);

        assertEquals(3, messageValidator.overallMessageCount());
        assertEquals(2,messageValidator .newMessagesReceived());
        assertEquals(1,messageValidator .oldMessagesReceived());
    }

    @Test
    public void testValidateSentMessage() {

        messageValidator.validateSentMessage(msg1.id);
        messageValidator.validateSentMessage(msg2.id);
        messageValidator.validateSentMessage(msg3.id);

        assertEquals(3, messageValidator.sentMessagesCount());
    }

    @Test
    public void testReset() {

        messageValidator.validateSentMessage(msg1.id);
        messageValidator.validateReceivedMessage(msg2);
        messageValidator.validateReceivedMessage(msg3);

        messageValidator.reset(2001l, 3000l);

        // overallMessageCount counts only received messages
        assertEquals(2, messageValidator.overallMessageCount());
        assertEquals(0, messageValidator.sentMessagesCount());
        assertEquals(0, messageValidator.newMessagesReceived());
        assertEquals(0, messageValidator.oldMessagesReceived());
    }
}
