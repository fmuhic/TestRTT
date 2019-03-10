package pitcher;

import com.google.gson.Gson;
import msg.Message;
import msg.MessageValidator;
import msg.PitcherMessageProcessor;

import java.util.function.Consumer;

public class PitcherCoordinator implements Consumer<String> {

    private NetworkMetrics networkMetrics;
    private Logger logger;
    private PitcherMessageProcessor msgProcessor;
    private MessageValidator validator;
    private Gson gson;

    public PitcherCoordinator(NetworkMetrics networkMetrics, Logger logger,
                              PitcherMessageProcessor msgProcessor, MessageValidator validator) {
        this.networkMetrics = networkMetrics;
        this.logger = logger;
        this.msgProcessor = msgProcessor;
        this.validator = validator;
        this.gson = new Gson();
    }

    @Override
    public void accept(String strMsg) {
        Message msg = gson.fromJson(strMsg, Message.class);
        msgProcessor.update(msg);
        validator.validateReceivedMessage(msg);
        networkMetrics.processMsg(msg);
    }

    public void update(long msgStartId, long msgEndId) {
        logger.logMetrics(networkMetrics, validator);
        networkMetrics.resetMetrics();
        validator.reset(msgStartId, msgEndId);
    }

    public void validateSentMessage(long msgId) {
        validator.validateSentMessage(msgId);
    }
}
