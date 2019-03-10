package msg;

public class Message {

    public long id;
    public long creationTime;  // Initial message creation time
    public long fetcherTime;   // Time message arrived at fetcher side
    public long returnedTime;  // Time message returned from fetcher to pitcher
    public String content;

    public Message(long id, long creationTime, long fetcherTime, long returnedTime, String content) {
        this.id = id;
        this.creationTime = creationTime;
        this.fetcherTime = fetcherTime;
        this.returnedTime = returnedTime;
        this.content = content;
    }
}
