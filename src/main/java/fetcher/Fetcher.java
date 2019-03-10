package fetcher;

import abstraction.Startable;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Fetcher implements Startable {

    private String bindAddress;
    private int port;
    private ResponseHandler responseHandler;

    public Fetcher(String bindAddress, int port, ResponseHandler responseHandler) {
        this.bindAddress = bindAddress;
        this.port = port;
        this.responseHandler = responseHandler;
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(bindAddress, port), 0);
            server.createContext("/", responseHandler);
            server.setExecutor(null); // creates a default executor
            server.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}