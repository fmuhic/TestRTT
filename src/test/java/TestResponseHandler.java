import com.sun.net.httpserver.HttpExchange;
import fetcher.ResponseHandler;
import msg.FetcherMessageProcessor;
import org.junit.jupiter.api.Test;
import util.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestResponseHandler {
    @Test
    public void testHandle() throws IOException {
        String msg = "{\"id\":1," +
                     "\"creationTime\":123456789," +
                     "\"fetcherTime\":-111111111111," +
                     "\"returnedTime\":-111111111111," +
                     "\"content\":\"xxxxxxxxxx\"}";

        Utility utility = mock(Utility.class);
        when(utility.convertStreamToString(any(InputStream.class))).thenReturn(msg);

        FetcherMessageProcessor fmp = mock(FetcherMessageProcessor.class);
        OutputStream ostream = mock(OutputStream.class);

        HttpExchange httpEx = mock(HttpExchange.class);
        when(httpEx.getResponseBody()).thenReturn(ostream);
        when(httpEx.getRequestBody()).thenReturn(mock(InputStream.class));

        ResponseHandler response = new ResponseHandler(fmp, utility);
        response.handle(httpEx);

        verify(ostream, times(1)).write(msg.getBytes());
        verify(ostream, times(1)).close();
    }
}
