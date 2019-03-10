package msg;

import java.util.ArrayList;

public class MessageValidator {

    private long startId;
    private long endId;
    private long overallMsgCount;
    private ArrayList<Long> sentMessages;
    private ArrayList<Long> newReceivedMsgs;
    private ArrayList<Long> oldReceivedMsgs;

    public MessageValidator(long msgStartId, long msgEndId) {
        reset(msgStartId, msgEndId);
        overallMsgCount = 0;
    }

    synchronized public void reset(long newMsgStartId, long newMsgEndId) {
        startId = newMsgStartId;
        endId = newMsgEndId;
        sentMessages = new ArrayList<>();
        newReceivedMsgs = new ArrayList<>();
        oldReceivedMsgs = new ArrayList<>();
    }

    synchronized public void validateReceivedMessage(Message msg) {
        overallMsgCount++;

        if(msg.id >= startId && msg.id < endId)
            newReceivedMsgs.add(msg.id);
        else
            oldReceivedMsgs.add(msg.id);
    }

    synchronized public void validateSentMessage(long msgId) {
        sentMessages.add(msgId);
    }
    synchronized public int sentMessagesCount() {
        return sentMessages.size();
    }
    synchronized public int newMessagesReceived() {
        return newReceivedMsgs.size();
    }
    synchronized public long overallMessageCount() {
        return overallMsgCount;
    }
    synchronized public int oldMessagesReceived() {
        return oldReceivedMsgs.size();
    }


}
