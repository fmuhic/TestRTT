package msg;

import abstraction.Updatable;
import util.DateTime;

public class FetcherMessageProcessor implements Updatable<Message> {

    private DateTime dateTime;

    public FetcherMessageProcessor(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void update(Message msg) {
        msg.fetcherTime = dateTime.currentTimeMs();
    }
}
