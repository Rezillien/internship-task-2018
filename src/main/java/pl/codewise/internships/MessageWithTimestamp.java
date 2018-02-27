package pl.codewise.internships;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

public class MessageWithTimestamp extends Message {
    private LocalDateTime timestamp;

    Clock clock = Clock.systemDefaultZone();

    public MessageWithTimestamp(Message message, Clock clock){
        super(message.getUserAgent(),message.getErrorCode());
        this.timestamp = LocalDateTime.now(clock);
    }

    public boolean isOlderThan(int numberOfMinutes){
        return Duration.between(timestamp, LocalDateTime.now(clock)).toMinutes() > numberOfMinutes;
    }

    public void setClock(Clock clock){
        this.clock = clock;
    }


}
