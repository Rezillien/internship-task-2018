package pl.codewise.internships;

import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MessageQueueTest {

    @Test
    public void isQueueReturningGoodValueForOneThread(){
        MessageQueue queue = new BasicConcurrentMessageQueue();
        queue.add(new Message("test", 404));
        queue.add(new Message("test", 504));
        queue.add(new Message("test", 304));
        queue.add(new Message("test", 404));
        queue.add(new Message("test", 304));
        assertEquals(3,queue.numberOfErrorMessages());

    }

    @Test
    public void isQueueConcurrent() throws InterruptedException {
        Thread[] readers = new Thread[20];
        Thread[] writers = new Thread[20];

        int times = 100;
        int threads = 20;

        MessageQueue queue = new BasicConcurrentMessageQueue();

        for(int i=0; i<threads;i++){
            readers[i] = new Thread(new ConcurrentQueueReader(queue,times));
            writers[i] = new Thread(new ConcurrentQueueWriter(queue, times));
        }

        for(int i=0; i<threads;i++) {
            readers[i].start();
            writers[i].start();
        }
        for(int i=0;i<threads;i++){
            readers[i].join();
            writers[i].join();
        }


        assertEquals(times*threads/2,queue.numberOfErrorMessages());

    }


    @Test
    public void checkIfQueueRemoveMessagesAfter5Minutes(){
        BasicConcurrentMessageQueue queue = new BasicConcurrentMessageQueue();
        for(int i=0;i<10;i++){
            //TODO: fix this injection
            Clock clock = Clock.fixed(Instant.now().minus(i, ChronoUnit.MINUTES), ZoneId.systemDefault());
            queue.setClock(clock);
            queue.add(new Message("test",404));
        }
        assertEquals(5,queue.numberOfErrorMessages());
    }



}