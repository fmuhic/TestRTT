package msg;

import abstraction.Updatable;
import util.DateTime;

public class PitcherMessageProcessor implements Updatable<Message> {

    private DateTime dateTime;

    public PitcherMessageProcessor(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void update(Message msg) {
        msg.returnedTime = dateTime.currentTimeMs();
    }
}
