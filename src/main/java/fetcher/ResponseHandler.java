package fetcher;

import com.google.gson.Gson;
import msg.FetcherMessageProcessor;
import msg.Message;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.Utility;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseHandler implements HttpHandler {

    private FetcherMessageProcessor msgProcessor;
    private Utility utility;
    private Gson gson;

    public ResponseHandler(FetcherMessageProcessor msgProcessor, Utility utility) {
        this.msgProcessor = msgProcessor;
        this.gson = new Gson();
        this.utility = utility;
    }

    public void handle(HttpExchange ex) {
        String strMsg = utility.convertStreamToString(ex.getRequestBody());
        Message msg = gson.fromJson(strMsg, Message.class);
        msgProcessor.update(msg);
        strMsg = gson.toJson(msg);

        try {
            ex.sendResponseHeaders(200, strMsg.length());
            OutputStream os = ex.getResponseBody();
            os.write(strMsg.getBytes());
            os.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
