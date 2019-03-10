# Round-Trip Time Test

This application measures round-trip time of a message sent between two computers using HTTP.

# Installation

- Make sure you have JRE 11 and Maven 3.6.0.
- Compile and bundle up application
```
 $ mvn install
```
- Run application as Fetcher (localhost):
```
 $ java -jar target\RTT-1.0.0.jar -f
```

- Run application as Pitcher (localhost):
```
 $ java -jar target\RTT-1.0.0.jar -p
```

- For additional command line arguments use -h
```
 $ java -jar target\RTT-1.0.0.jar -h
```

- Run tests
```
 $ mvn test
```


# Output example Fetcher
```
Started application in Fetcher mode. Listening at http://127.0.0.1:8000
```

# Output example Pitcher (localhost test, minimal delay)
```
03-09-2019 16:47:39.562: OverallMessageCount: 13000, MessagesSent: 1000
03-09-2019 16:47:39.562: MessagesReceived: 1000, OldMessagesReceived: 0
03-09-2019 16:47:39.562: RTT: 0.309, A->B: 0.186, B->A: 0.12299999
03-09-2019 16:47:40.563: OverallMessageCount: 14000, MessagesSent: 1000
03-09-2019 16:47:40.563: MessagesReceived: 1000, OldMessagesReceived: 0
03-09-2019 16:47:40.563: RTT: 0.29700023, A->B: 0.16299993, B->A: 0.13400012
03-09-2019 16:47:41.564: OverallMessageCount: 15000, MessagesSent: 1000
03-09-2019 16:47:41.564: MessagesReceived: 1000, OldMessagesReceived: 0
03-09-2019 16:47:41.564: RTT: 0.27299994, A->B: 0.16200009, B->A: 0.11100004
```

# Legend

- OverallMessageCount - Number of messages sent since application started
- MessagesSent - Number of messages sent since last print
- MessagesReceived - Portion of sent messages which are received in this print interval (no delays)
- OldMessagesReceived - Delayed messages from previous print intervals (sometimes RTT is bigger than print interval)
- RTT - Round-trip time in ms (A->B + B->A = RTT).
- A->B - Time it takes for message to go from Pitcher to Fetcher in ms.
- B->A - Time it takes for message to return from Fetcher to Pitcher in ms.
