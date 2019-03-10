package pitcher;

import abstraction.Creator;
import msg.MessageFactory;
import util.DateTime;

import java.net.http.HttpClient;
public class Pinger implements Runnable {

    private MessageSender messageService;
    private int msgPerSec;
    private Creator<String> msgFactory;
    private PitcherCoordinator coordinator;
    private DateTime dateTime;

    public Pinger(MessageSender messageService, int msgPerSec, Creator<String> msgFactory,
                  PitcherCoordinator coordinator, DateTime dateTime) {
        this.messageService = messageService;
        this.coordinator = coordinator;
        this.msgFactory = msgFactory;
        this.msgPerSec = msgPerSec;
        this.dateTime = dateTime;
    }

    public void run() {
        while(true) {
            long start = dateTime.currentTimeMs();

            ping();

            long elapsed = dateTime.currentTimeMs() - start;
            try {
                Thread.sleep(1000 - elapsed > 0 ? 1000 - elapsed : 0);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void ping() {
        coordinator.update(
                MessageFactory.currentMsgId() + 1,
                MessageFactory.currentMsgId() + msgPerSec + 1
        );

        for(int i = 0; i < msgPerSec; i++) {
            String msg = msgFactory.create();
            messageService.send(msg, coordinator);
            coordinator.validateSentMessage(MessageFactory.currentMsgId());
        }
    }
}
