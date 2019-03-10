package msg;

import abstraction.Creator;
import com.google.gson.Gson;
import util.DateTime;

public class MessageFactory implements Creator<String> {

    private static long msgID = 10_000_000_000_000l;
    private static int minMsgLength = 120;

    private Gson gson;
    private int msgLength;
    private String repeatableChar;
    private String msgContent;
    private DateTime dateTime;

    public MessageFactory(int msgLength, char repeatableChar, DateTime dateTime) {
        this.gson = new Gson();
        if(msgLength < minMsgLength)
            this.msgLength = 0;
        else
            this.msgLength = msgLength - minMsgLength;
        this.repeatableChar = Character.toString(repeatableChar);
        this.msgContent = this.repeatableChar.repeat(this.msgLength); // Java 11 <3
        this.dateTime = dateTime;
    }

    @Override
    public String create() {
        msgID++;
        long creationTime = dateTime.currentTimeMs();
        long fetcherTime = -111111111111l;
        long returnedTime = -111111111111l;
        Message msg = new Message(msgID, creationTime, fetcherTime, returnedTime, msgContent);

        return gson.toJson(msg, Message.class) ;
    }

    public static long currentMsgId() {
        return msgID;
    }
    public static long minMsgLength() {
        return minMsgLength;
    }
}
