package pl.codewise.internships;

import java.util.Random;

public class ConcurrentQueueReader implements Runnable {
    private MessageQueue queue;
    private int times;
    private Random random = new Random();

    public ConcurrentQueueReader(MessageQueue queue, int times) {
        this.queue = queue;
        this.times = times;
    }

    @Override
    public void run() {
        for (int i = 0; i < times/2; i++) {
            queue.snapshot();
            queue.numberOfErrorMessages();
        }
    }
}
