import abstraction.Startable;
import fetcher.Fetcher;
import fetcher.ResponseHandler;
import msg.*;
import pitcher.*;
import util.Args;
import util.DateTime;
import util.Printer;
import util.Utility;

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
