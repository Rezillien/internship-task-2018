package pl.codewise.internships;

import java.time.Duration;
import java.time.LocalDateTime;

public class MessageWithTimestamp extends Message {
    private LocalDateTime timestamp;

    public MessageWithTimestamp(String userAgent, int errorCode) {
        super(userAgent, errorCode);
        timestamp = LocalDateTime.now();
    }

    public MessageWithTimestamp(String userAgent, int errorCode, LocalDateTime timestamp){
        super(userAgent,errorCode);
        this.timestamp = timestamp;
    }

    public MessageWithTimestamp(Message message){
        super(message.getUserAgent(),message.getErrorCode());
        this.timestamp = LocalDateTime.now();
    }

    public boolean isOlderThan(int numberOfMinutes){
        return Duration.between(timestamp, LocalDateTime.now()).toMinutes() > numberOfMinutes;
    }


}
