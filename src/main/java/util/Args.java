package util;

import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(name = "RTT", description = "Application calculates and displays network throughput metrics.")
public class Args {
    @Option(names = {"-f", "--fetcher"}, description = "Start application in fetcher mode")
    public boolean fetcher;

    @Option(names = {"-p", "--pitcher"}, description = "Start application in pitcher mode")
    public boolean pitcher;

    @Option(names = "--port", description = "TCP socket port. Pitcher will use it for connect and Fetcher will use it for listen (default: ${DEFAULT-VALUE})")
    public int port  = 8000;

    @Option(names = "--bind", description = "TCP socket address. Fetcher will use it for listen (default: ${DEFAULT-VALUE})")
    public String bind  = "127.0.0.1";

    @Option(names = "--hostname", description = "Pitcher's hostname (default: ${DEFAULT-VALUE})")
    public String hostname = "127.0.0.1";

    @Option(names = "--mps", description = "Messages per second. Specify how many messages will Pitcher send in one second (default: ${DEFAULT-VALUE})")
    public int mps = 50;

    @Option(names = "--size", description = "Messages size in bytes (default: ${DEFAULT-VALUE}, min: 120)")
    public int msgSize  = 200;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    public boolean helpRequested;

    public CommandLine commandLine;

    public void parse(String[] args) {
        commandLine = new CommandLine(this);
        commandLine.parse(args);
    }
}

