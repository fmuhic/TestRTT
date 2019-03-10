import msg.Message;
import msg.MessageValidator;
import msg.PitcherMessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pitcher.Logger;
import pitcher.NetworkMetrics;
import pitcher.PitcherCoordinator;

import static org.mockito.Mockito.*;

public class TestPitcherCoordinator {

    private NetworkMetrics networkMetrics;
    private Logger logger;
    private PitcherMessageProcessor msgProcessor;
    private MessageValidator validator;
    private PitcherCoordinator coordinator;

    @BeforeEach
    public void setUp() {
        networkMetrics = mock(NetworkMetrics.class);
        logger = mock(Logger.class);
        msgProcessor = mock(PitcherMessageProcessor.class);
        validator = mock(MessageValidator.class);

        coordinator = new PitcherCoordinator(networkMetrics, logger, msgProcessor, validator);
    }

    @Test
    public void testUpdate() {

        coordinator.update(1000l, 2000l);

        verify(logger, times(1)).logMetrics(any(NetworkMetrics.class), any(MessageValidator.class));
        verify(networkMetrics, times(1)).resetMetrics();
        verify(validator, times(1)).reset(anyLong(), anyLong());
    }

    @Test
    public void testValidateSentMessage() {

        coordinator.validateSentMessage(1234l);
        verify(validator, times(1)).validateSentMessage(1234l);
    }

    @Test
    public void testAccept() {

        String msg = "{\"id\":1," +
                "\"creationTime\":123456789," +
                "\"fetcherTime\":-111111111111," +
                "\"returnedTime\":-111111111111," +
                "\"content\":\"xxxxxxxxxx\"}";

        coordinator.accept(msg);

        verify(msgProcessor, times(1)).update(any(Message.class));
        verify(validator, times(1)).validateReceivedMessage(any(Message.class));
        verify(networkMetrics, times(1)).processMsg(any(Message.class));
    }
}
