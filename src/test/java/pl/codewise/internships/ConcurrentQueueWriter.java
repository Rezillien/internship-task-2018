package pl.codewise.internships;

import java.util.Random;

public class ConcurrentQueueWriter implements Runnable{
    private MessageQueue queue;
    private int times;
    private Random random = new Random();

    public ConcurrentQueueWriter(MessageQueue queue, int times){
        this.queue = queue;
        this.times = times;
    }

    @Override
    public void run() {
        for(int i=0;i<times/2;i++){
            queue.add(new Message("test", random.nextInt()%100+200));
            queue.add(new Message("test", random.nextInt()%50+500));
        }
    }
}
