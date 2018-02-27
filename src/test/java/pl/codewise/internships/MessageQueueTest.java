package pl.codewise.internships;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MessageQueueTest {

    @Test
    public void isQueueReturningGoodValueForOneThread(){
        MessageQueue queue = new BlockingConcurrentMessageQueue();
        queue.add(new Message("test", 404));
        queue.add(new Message("test", 504));
        queue.add(new Message("test", 304));
        queue.add(new Message("test", 404));
        queue.add(new Message("test", 304));
        assertEquals(3,queue.numberOfErrorMessages());

    }

    @Test
    public void isBlockingQueueConcurrent() throws InterruptedException {
        Thread[] readers = new Thread[20];
        Thread[] writers = new Thread[20];

        int times = 100;
        int threads = 20;

        MessageQueue queue = new BlockingConcurrentMessageQueue();

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



}