package pitcher;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MessageSender {

    private String hostname;
    private HttpClient client;

    public MessageSender(String hostname, int port) {
        this.hostname = "http://" + hostname + ":" + port;
        this.client = HttpClient.newHttpClient();
    }

    public void send(String message, PitcherCoordinator coordinator) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(hostname))
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(coordinator)
                .join();
    }
}
