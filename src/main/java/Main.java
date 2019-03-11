import abstraction.Startable;
import fetcher.Fetcher;
import fetcher.ResponseHandler;
import msg.*;
import pitcher.*;
import util.Args;
import util.DateTime;
import util.Printer;
import util.Utility;

// Developer notes:
// Fetcher is a simple service which starts HTTP server and waits for connections.
// After connection is established with Pitcher, Fetcher will continuously receive
// json formated messages and delegate them to ResponseHandler which will augment them
// and send them back to Pitcher.
// Pitcher is service that does multiple things: Asynchronously sends messages
// in continuous intervals to Fetcher, receive those messages back from Fetcher,
// validate them, update network metrics and print those metrics to the screen.
// Best way to implement all those services is to run them independently
// on different threads and implement communication through common interfaces.
// This was the initial design. However, as it turns out, runnig all those services
// inependently adds additional layer of complexity and contibutes to problems such as:
// - Sometimes print service will run and requst metrics information exactly
//   in the middle of message sending operation. This will print correct, but misleading,
//   informations based on half messages sent and even less messages received back.
//   This happens becase threads in charge of  printing and sending operation run
//   in different and independent intervals which always change
//   (since Thread.sleep(amount) never sleeps thread for exactly specified time amount).
//   This is not only problem with print service, but all other services as well and
//   and solving this problem is far from a trivial task.
// - Sending messages synchronousley it out of question since we can't affort ourselves to
//   wait for response and process it then (if RTT is 0.75 sec we will only be able to send
//   and proces 1.5 message per sec on average). Now, since we are sending messages
//   asynchronously, we are not guaranteed to receive them back in order,
//   so how do we know if we missed some messags? (If we don't get message, maybe
//   its just delayed, and if we wait additional 100ms maybe we will get it).
//   How long do we wait?
// - System behaves differently depending on input parameters: message size, message count
//   per second, server latency, underlying hardware on which applications run.
//   If we sent 10 000 messages per sec of 1 KB each, can we expect same behaviour as
//   if we sent 100 messages of 200 Bytes each?
// Now, all those problem get amplified by a great amount if we have 5 threads running in parallel.
// So, I decided to take another route and put most of the Pitcher coordination logic
// in single thread (class Pinger) and almost everything is run inside it's run method.
// It does kinda break single responsibility principle but it reduces complexity by a 
// good amount, in case anyone cares about explanation.

public class Main {
    public static void main(String[] arguments) {
        Args args = new Args();
        args.parse(arguments);
        DateTime dateTime = new DateTime();

        Startable service = new InvalidService();

        if(args.pitcher && !args.fetcher) {
            NetworkMetrics networkMetrics = new NetworkMetrics();
            Printer printer = new Printer();
            Logger logger = new Logger(printer, dateTime);
            PitcherMessageProcessor msgProcessor = new PitcherMessageProcessor(dateTime);
            MessageValidator validator = new MessageValidator(
                    MessageFactory.currentMsgId() + 1,
                    MessageFactory.currentMsgId() + args.mps + 1
            );
            PitcherCoordinator coordinator = new PitcherCoordinator(networkMetrics, logger, msgProcessor, validator);

            MessageFactory msgFactory = new MessageFactory(args.msgSize, 'x', dateTime);
            MessageSender messageSender = new MessageSender(args.hostname, args.port);

            Pinger pinger = new Pinger(messageSender, args.mps, msgFactory, coordinator, dateTime);

            service = new Pitcher(pinger);
        }
        else if(args.fetcher && !args.pitcher) {
            FetcherMessageProcessor msgProcessor = new FetcherMessageProcessor(dateTime);
            Utility utility = new Utility();
            ResponseHandler responseHandler = new ResponseHandler(msgProcessor, utility);

            System.out.println("Started application in Fetcher mode. Listening at http://" + args.bind + ":" + args.port);
            service = new Fetcher(args.bind, args.port, responseHandler);
        }

        if(args.helpRequested)
            args.commandLine.usage(System.out);
        else
            service.start();
    }
}

class InvalidService implements Startable {

    @Override
    public void start() {
        System.out.println("Invalid service selected, please use only Fetcher or Pitcher");
    }
}
