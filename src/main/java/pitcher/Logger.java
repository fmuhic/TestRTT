package pitcher;

import abstraction.Printable;
import msg.MessageValidator;
import util.DateTime;

public class Logger {

    private Printable printer;
    private DateTime dateTime;

    public Logger(Printable printer, DateTime dateTime) {
        this.printer = printer;
        this.dateTime = dateTime;
    }

    public void logMetrics(NetworkMetrics networkMetrics, MessageValidator validator) {
        String dateString = dateTime.currentDateTime();
        printer.print(dateString + ": OverallMessageCount: " + validator.overallMessageCount() +
                ", MessagesSent: " + validator.sentMessagesCount() +
                ", MessagesReceived: " + validator.newMessagesReceived() +
                ", OldMessagesReceived: " + validator.oldMessagesReceived());
        printer.print(dateString + ": RTT: " + networkMetrics.avgRTT() +
                ", A->B: " + networkMetrics.avgAB() +
                ", B->A: " + networkMetrics.avgBA());
    }
}
